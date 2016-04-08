package com.holler.holler_service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.holler.bean.LoginDTO;
import com.holler.bean.SignUpDTO;
import com.holler.bean.SignUpResponseDTO;
import com.holler.bean.TagDTO;
import com.holler.bean.UserDTO;
import com.holler.bean.UserJobDTO;
import com.holler.bean.UserLocationDTO;
import com.holler.bean.UserSettingDTO;
import com.holler.holler_dao.TagDao;
import com.holler.holler_dao.UserDao;
import com.holler.holler_dao.common.HollerConstants;
import com.holler.holler_dao.entity.Tags;
import com.holler.holler_dao.entity.User;
import com.holler.holler_dao.util.CommonUtil;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserDao userDao;
	
	@Autowired
	TagDao tagDao;
	
	@Autowired
	OTPService otpService;
	
	@Autowired
	TokenService tokenService;
	
	public boolean authenticateUser(String email, String password){
		return userDao.authenticateUser(email, password);
	}
	
	public boolean authenticateUserWithPhoneNumber(String email, String phoneNumber){
		return userDao.authenticateUserWithPhoneNumber(email, phoneNumber);
	}
	
	public UserJobDTO getUserJobs(User loggedInUser, int requestUserId){
		UserJobDTO userJobDTO = null;
		if(loggedInUser != null && loggedInUser.getId() == requestUserId){
			List<Object[]> userJobs = userDao.getUserJobs(requestUserId); 
			List<UserJobDTO> userJobDTOs = UserJobDTO.constructUserJobDTO(userJobs);
			userJobDTO = userJobDTOs.get(0);
		}else{
			userJobDTO = null;
		}
		return userJobDTO;
	}

	public Map<String, Object> signInUser(String phoneNumber, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		User user = userDao.getByPhoneNumber(phoneNumber);
		if(user != null){
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			result.put(HollerConstants.SUCCESS, HollerConstants.LOGIN_SUCCESS);
			result.put("loggedInUserId", user.getId());
		}else{
			result.put(HollerConstants.FAILURE, HollerConstants.INVALID_CREDENTIALS);
		}
		return result;
	}

	public Map<String, String> signOutUser(HttpServletRequest request) {
		Map<String, String> result = new HashMap<String, String>();
		HttpSession session = request.getSession(false);
		if(session != null){
            session.invalidate();
        }
		result.put(HollerConstants.SUCCESS, HollerConstants.LOGOUT_SUCCESS);
		return result;
	}
	
	public Map<String, Object> signUpUser(SignUpDTO signUpDTO, HttpServletRequest request) {
		boolean isValidOtp = otpService.validateOtp(signUpDTO.getPhoneNumber(), signUpDTO.getOtp());
		Map<String, Object> result = new HashMap<String, Object>();
		if(!isValidOtp){
			result.put(HollerConstants.STATUS, HollerConstants.FAILURE);
			result.put(HollerConstants.MESSAGE, HollerConstants.OTP_SIGNUP_FAILURE);
			return result;
		}
		try {
			if(isUserPresent(signUpDTO.getEmail(), signUpDTO.getPhoneNumber())){
				result.put(HollerConstants.STATUS, HollerConstants.FAILURE);
				result.put(HollerConstants.MESSAGE, HollerConstants.DUPLICATE_USER);
			}else{
				User user = User.constructUserForSignUp(signUpDTO.getName(), signUpDTO.getEmail(), signUpDTO.getPhoneNumber());
				userDao.save(user);
				
				Map<String, Object> tokenResult = tokenService.generateToken(signUpDTO.getEmail(), signUpDTO.getPhoneNumber());
				SignUpResponseDTO signUpResponseDTO = new SignUpResponseDTO((String)tokenResult.get("token"),
						user.getId(),user.getEmail(), user.getPhoneNumber(), user.getName(), user.getPic());
				
				result.put(HollerConstants.STATUS, HollerConstants.SUCCESS);
				result.put(HollerConstants.RESULT, signUpResponseDTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put(HollerConstants.STATUS, HollerConstants.FAILURE);
			result.put(HollerConstants.MESSAGE, HollerConstants.SIGNUP_FAILURE);
		}
		return result;
	}

	public Map<String, Object> getUserProfile(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(tokenService.isValidToken(request)){
		// if(Boolean.TRUE){
			User user = userDao.findByIdWithTags(Integer.valueOf(request.getHeader("userId")));
			UserDTO userDTO = UserDTO.getDtoForUserProfile(user);
			result.put(HollerConstants.STATUS, HollerConstants.SUCCESS);
			result.put(HollerConstants.RESULT, userDTO);
		 }else{
			result.put(HollerConstants.STATUS, HollerConstants.FAILURE);
			result.put(HollerConstants.MESSAGE, HollerConstants.TOKEN_VALIDATION_FAILED);
		}
		return result;
	}
	
	public Map<String, Object> updateUserProfile(UserDTO userDTO, HttpServletRequest request){
		Map<String, Object> result = new HashMap<String, Object>();
		if(tokenService.isValidToken(request)){
		// if(Boolean.TRUE){
			User user = userDao.findById(userDTO.getUserId());
			UserDTO.setUserDataToUpdate(userDTO, user);
			Set<Tags> tags = new HashSet<Tags>(tagDao.findbyIds(userDTO.getTags()));
			user.setTags(tags);
			userDao.update(user);
			setTagsMapInUserDto(tags, userDTO);
			result.put(HollerConstants.STATUS, HollerConstants.SUCCESS);
			result.put(HollerConstants.RESULT, userDTO);
		 }else{
			result.put(HollerConstants.STATUS, HollerConstants.FAILURE);
			result.put(HollerConstants.MESSAGE, HollerConstants.TOKEN_VALIDATION_FAILED);
		}
		return result;
	}

	private void setTagsMapInUserDto(Set<Tags> tags, UserDTO userDTO) {
		Map<Integer, String> tagMaps = new HashMap<Integer, String>();
		for (Tags tag : CommonUtil.safe(tags)) {
            tagMaps.put(tag.getId(), tag.getTagName());
        }
		userDTO.setTagsMap(tagMaps);
	}

	public boolean isUserPresent(String email, String phoneNumber) {
		boolean isUserPresent = userDao.checkIfUserExists(email, phoneNumber);
		return isUserPresent;
	}

	public List<TagDTO> fetchTagsForUserHomePage(Integer userId) {
		List<Tags> tags = tagDao.fetchTagsForUserHomePage(userId);
		List<TagDTO> tagDTOs = TagDTO.getTagDTOsFromTags(tags);
		return tagDTOs;
	}

	public Map<String, Object> updateUserCurrentLocationAndAddress(UserLocationDTO userLocationDTO, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(tokenService.isValidToken(request)){
		// if(Boolean.TRUE){
			User user = userDao.findById(userLocationDTO.getUserId());
			String currentLocation = UserLocationDTO.getLocationInCommaSeparatedString(userLocationDTO);
			String currentAddress = UserLocationDTO.getAddressFromLocation(userLocationDTO);

			user.setCurrentLocation(currentLocation);
			user.setCurrentAddress(currentAddress);
			userDao.update(user);
			result.put(HollerConstants.STATUS, HollerConstants.SUCCESS);
			result.put(HollerConstants.RESULT, Boolean.TRUE);
		 }else{
			result.put(HollerConstants.STATUS, HollerConstants.FAILURE);
			result.put(HollerConstants.MESSAGE, HollerConstants.TOKEN_VALIDATION_FAILED);
		}
		return result;
	}
	
	public Map<String, Object> getUserSettings(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(tokenService.isValidToken(request)){
		// if(Boolean.TRUE){
			User user = userDao.findByIdWithTags(Integer.valueOf(request.getHeader("userId")));
			UserSettingDTO userSettingResponseDTO = UserSettingDTO.getDtoForUserSetting(user);
			result.put(HollerConstants.STATUS, HollerConstants.SUCCESS);
			result.put(HollerConstants.RESULT, userSettingResponseDTO);
		 }else{
			result.put(HollerConstants.STATUS, HollerConstants.FAILURE);
			result.put(HollerConstants.MESSAGE, HollerConstants.TOKEN_VALIDATION_FAILED);
		}
		return result;
	}
	
	public Map<String, Object> updateUserSetting(UserSettingDTO userSettingRequestDTO, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(tokenService.isValidToken(request)){
		// if(Boolean.TRUE){
			User user = userDao.findById(userSettingRequestDTO.getUserId());
			user.setJobDiscoveryLimit(userSettingRequestDTO.getJobDiscoveryLimit());
			user.setPushNotification(userSettingRequestDTO.getPushNotification());
			user.setCompensationRange(userSettingRequestDTO.getCompensationRange());
			userDao.update(user);
			result.put(HollerConstants.STATUS, HollerConstants.SUCCESS);
			result.put(HollerConstants.RESULT, Boolean.TRUE);
		 }else{
			result.put(HollerConstants.STATUS, HollerConstants.FAILURE);
			result.put(HollerConstants.MESSAGE, HollerConstants.TOKEN_VALIDATION_FAILED);
		}
		return result;
	}

	public Map<String, Object> loginUser(LoginDTO loginDTO, HttpServletRequest request) {
		boolean isValidOtp = otpService.validateOtp(loginDTO.getPhoneNumber(), loginDTO.getOtp());
		Map<String, Object> result = new HashMap<String, Object>();
		if(!isValidOtp){
			result.put(HollerConstants.STATUS, HollerConstants.FAILURE);
			result.put(HollerConstants.MESSAGE, HollerConstants.OTP_SIGNUP_FAILURE);
			return result;
		}
		try {
			User user = userDao.getByPhoneNumber(loginDTO.getPhoneNumber());
			if(user != null){
				Map<String, Object> tokenResult = tokenService.generateToken(loginDTO.getEmail(), loginDTO.getPhoneNumber());
				SignUpResponseDTO signUpResponseDTO = new SignUpResponseDTO((String)tokenResult.get("token"),
						user.getId(),user.getEmail(), user.getPhoneNumber(), user.getName(), user.getPic());
				result.put(HollerConstants.STATUS, HollerConstants.SUCCESS);
				result.put(HollerConstants.RESULT, signUpResponseDTO);
			}else{
				result.put(HollerConstants.STATUS, HollerConstants.FAILURE);
				result.put(HollerConstants.MESSAGE, HollerConstants.USER_NOT_FOUND);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put(HollerConstants.STATUS, HollerConstants.FAILURE);
			result.put(HollerConstants.MESSAGE, HollerConstants.LOGIN_FAILURE);
		}
		return result;
	}



}
