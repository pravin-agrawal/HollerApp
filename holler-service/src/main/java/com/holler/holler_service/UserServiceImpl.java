package com.holler.holler_service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.holler.bean.*;
import com.holler.holler_dao.entity.UserDetails;
import com.holler.twilioSms.SmsSender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.holler.holler_dao.TagDao;
import com.holler.holler_dao.UserDao;
import com.holler.holler_dao.common.HollerConstants;
import com.holler.holler_dao.entity.Tags;
import com.holler.holler_dao.entity.User;
import com.holler.holler_dao.util.CommonUtil;

@Service
public class UserServiceImpl implements UserService{
	static final Logger log = LogManager.getLogger(UserServiceImpl.class.getName());
	@Autowired
	UserDao userDao;
	
	@Autowired
	TagDao tagDao;
	
	@Autowired
	OTPService otpService;
	
	@Autowired
	TokenService tokenService;

	@Autowired
	SmsSender smsSender;
	
	public boolean authenticateUser(String email, String password){
		log.info("authenticateUser :: called");
		boolean isAuthenticatedUser = userDao.authenticateUser(email, password);
		log.info("authenticateUser :: is user authentic {}", isAuthenticatedUser);
		return isAuthenticatedUser;
	}
	
	public boolean authenticateUserWithEmail(String email){
		log.info("authenticateUserWithEmail :: called");
		boolean isAuthenticatedUser = userDao.authenticateUserWithEmail(email);
		log.info("authenticateUser :: is user authentic {}", isAuthenticatedUser);
		return isAuthenticatedUser;
	}

	public boolean authenticateUserWithPhoneNumber(String phoneNumber){
		log.info("authenticateUserWithEmail :: called");
		boolean isAuthenticatedUser = userDao.authenticateUserWithPhoneNumber(phoneNumber);
		log.info("authenticateUser :: is user authentic {}", isAuthenticatedUser);
		return isAuthenticatedUser;
	}
	
	public UserJobDTO getUserJobs(User loggedInUser, int requestUserId){
		log.info("getUserJobs :: called");
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
		log.info("signUpUser :: called");
		boolean isValidOtp = otpService.validateOtp(signUpDTO.getPhoneNumber(), signUpDTO.getOtp());
		log.info("signUpUser :: is valid otp {}", isValidOtp);
		Map<String, Object> result = new HashMap<String, Object>();
		if(!isValidOtp){
			result.put(HollerConstants.STATUS, HollerConstants.FAILURE);
			result.put(HollerConstants.MESSAGE, HollerConstants.OTP_SIGNUP_FAILURE);
			return result;
		}
		try {
			if(isUserPresent(signUpDTO.getEmail().toLowerCase().trim())){
				log.info("signUpUser :: user already present");
				result.put(HollerConstants.STATUS, HollerConstants.FAILURE);
				result.put(HollerConstants.MESSAGE, HollerConstants.DUPLICATE_USER);
			}else{
				log.info("signUpUser :: creating new user with name " + signUpDTO.getName() + " and email " + signUpDTO.getEmail().toLowerCase() + " and phoneNumber " + signUpDTO.getPhoneNumber());
				String name = signUpDTO.getName().substring(0,1).toUpperCase() + signUpDTO.getName().substring(1);
				User user = User.constructUserForSignUp(name, signUpDTO.getEmail().toLowerCase().trim(), signUpDTO.getPhoneNumber(), HollerConstants.PLATFORM_HOLLER);
				UserDetails ud = new UserDetails();
				user.setUserDetails(ud);
				userDao.save(user);
				
				Map<String, Object> tokenResult = tokenService.generateToken(signUpDTO.getEmail().toLowerCase().trim());
				SignUpResponseDTO signUpResponseDTO = new SignUpResponseDTO((String)tokenResult.get("token"),
						user.getId(),user.getEmail(), user.getPhoneNumber(), user.getName(), user.getPic());
				
				result.put(HollerConstants.STATUS, HollerConstants.SUCCESS);
				result.put(HollerConstants.RESULT, signUpResponseDTO);
				smsSender.sendWelcomeMsgSMS(signUpDTO.getPhoneNumber(), name);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put(HollerConstants.STATUS, HollerConstants.FAILURE);
			result.put(HollerConstants.MESSAGE, HollerConstants.SIGNUP_FAILURE);
		}
		return result;
	}
	
	public Map<String, Object> loginWithSocialPlatform(LoginWithSocialPlatformDTO loginWithSocialPlatformDTO, HttpServletRequest request) {
		log.info("loginWithSocialPlatform :: called");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			User user = null;
			SignUpResponseDTO signUpResponseDTO = null;
			if(isUserPresent(loginWithSocialPlatformDTO.getEmail().toLowerCase().trim())){
				log.info("signUpUser :: user already present");
				user = userDao.getByEmail(loginWithSocialPlatformDTO.getEmail().toLowerCase().trim());
			}else{
				log.info("signUpUser :: creating new user with name " + loginWithSocialPlatformDTO.getName() + " and email " + loginWithSocialPlatformDTO.getEmail() + " on platform " + loginWithSocialPlatformDTO.getPlatform());
				user = User.constructUserForSocialPlatformSignUp(loginWithSocialPlatformDTO.getName(), loginWithSocialPlatformDTO.getEmail().toLowerCase().trim(),
						loginWithSocialPlatformDTO.getGender(), loginWithSocialPlatformDTO.getProfilePic(), loginWithSocialPlatformDTO.getPlatform());
				UserDetails ud = new UserDetails();
				user.setUserDetails(ud);
				userDao.save(user);
			}
			Map<String, Object> tokenResult = tokenService.generateToken(loginWithSocialPlatformDTO.getEmail().toLowerCase().trim());
			signUpResponseDTO = new SignUpResponseDTO((String)tokenResult.get("token"),
					user.getId(),user.getEmail(), user.getPhoneNumber(), user.getName(), user.getPic(), user.getRating(),user.isUserVerified());
			result.put(HollerConstants.STATUS, HollerConstants.SUCCESS);
			result.put(HollerConstants.RESULT, signUpResponseDTO);
		} catch (Exception e) {
			e.printStackTrace();
			result.put(HollerConstants.STATUS, HollerConstants.FAILURE);
			result.put(HollerConstants.MESSAGE, HollerConstants.SIGNUP_FAILURE);
		}
		return result;
	}

	public Map<String, Object> getUserProfile(HttpServletRequest request) {
		log.info("getUserProfile :: called");
		Map<String, Object> result = new HashMap<String, Object>();
		if(tokenService.isValidToken(request)){
			//if(Boolean.TRUE){
			log.info("getUserProfile :: valid token");
			log.info("getUserProfile :: fetch profile for user {}", request.getHeader("userId"));
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
		log.info("updateUserProfile :: called");
		Map<String, Object> result = new HashMap<String, Object>();
		if(tokenService.isValidToken(request)){
		// if(Boolean.TRUE){
			log.info("updateUserProfile :: valid token");
			log.info("updateUserProfile :: update profile for user {}", userDTO.getUserId());
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
		log.info("isUserPresent :: valid token");
		boolean isUserPresent = userDao.checkIfUserExists(email, phoneNumber);
		log.info("isUserPresent :: is user present {}", isUserPresent);
		return isUserPresent;
	}
	
	public boolean isUserPresent(String email) {
		boolean isUserPresent = userDao.checkIfUserExists(email);
		log.info("isUserPresent :: is user present {}", isUserPresent);
		return isUserPresent;
	}

	public boolean isUserPresentWithPhoneNumber(String phoneNumber) {
		boolean isUserPresent = userDao.checkIfUserExistsWithPhoneNumber(phoneNumber);
		log.info("isUserPresent :: is user present {}", isUserPresent);
		return isUserPresent;
	}

	public List<TagDTO> fetchTagsForUserHomePage(Integer userId) {
		log.info("fetchTagsForUserHomePage :: called");
		log.info("fetchTagsForUserHomePage :: for user {}", userId);
		List<Tags> tags = tagDao.fetchTagsForUserHomePage(userId);
		List<TagDTO> tagDTOs = TagDTO.getTagDTOsFromTags(tags);
		return tagDTOs;
	}

	public Map<String, Object> updateUserCurrentLocationAndAddress(UserLocationDTO userLocationDTO, HttpServletRequest request) {
		log.info("updateUserCurrentLocationAndAddress :: called");
		Map<String, Object> result = new HashMap<String, Object>();
		if(tokenService.isValidToken(request)){
		// if(Boolean.TRUE){
			log.info("updateUserCurrentLocationAndAddress :: valid token");
			log.info("updateUserCurrentLocationAndAddress :: for user {}", userLocationDTO.getUserId());
			User user = userDao.findById(userLocationDTO.getUserId());
			String currentLocation = UserLocationDTO.getLocationInCommaSeparatedString(userLocationDTO);
			String currentAddress = UserLocationDTO.getAddressFromLocation(userLocationDTO);

			log.info("updateUserCurrentLocationAndAddress :: for user " + userLocationDTO.getUserId() + " currLoc is " + currentLocation + " and currAddr is " + currentAddress);
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
		log.info("getUserSettings :: called");
		Map<String, Object> result = new HashMap<String, Object>();
		//if(tokenService.isValidToken(request)){
		 if(Boolean.TRUE){
			log.info("getUserSettings :: valid token");
			log.info("getUserSettings :: for user {}", request.getHeader("userId"));
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
		log.info("updateUserSetting :: called");
		Map<String, Object> result = new HashMap<String, Object>();
		if(tokenService.isValidToken(request)){
		 //if(Boolean.TRUE){
			log.info("updateUserSetting :: valid token");
			log.info("updateUserSetting :: for user {}", userSettingRequestDTO.getUserId());
			User user = userDao.findById(userSettingRequestDTO.getUserId());
			user.getUserDetails().setJobDiscoveryLimit(userSettingRequestDTO.getJobDiscoveryLimit());
			user.getUserDetails().setPushNotification(userSettingRequestDTO.getPushNotification());
			user.getUserDetails().setCompensationRangeMin(userSettingRequestDTO.getCompensationRangeMin());
			user.getUserDetails().setCompensationRangeMax(userSettingRequestDTO.getCompensationRangeMax());
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
		log.info("loginUser :: called");
		Map<String, Object> result = new HashMap<String, Object>();
		boolean isValidOtp = otpService.validateOtp(loginDTO.getPhoneNumber(), loginDTO.getOtp());
		if(!isValidOtp){
		//if(!Boolean.TRUE){
			log.info("loginUser :: otp entered is invalid");
			result.put(HollerConstants.STATUS, HollerConstants.FAILURE);
			result.put(HollerConstants.MESSAGE, HollerConstants.OTP_SIGNUP_FAILURE);
			return result;
		}
		try {
			log.info("loginUser :: otp entered is valid");
			log.info("loginUser :: try to login user with email {}", loginDTO.getEmail());
			User user = userDao.getByEmail(loginDTO.getEmail().toLowerCase().trim());
			if(user != null){
				Map<String, Object> tokenResult = tokenService.generateToken(loginDTO.getEmail().toLowerCase().trim());
				SignUpResponseDTO signUpResponseDTO = new SignUpResponseDTO((String)tokenResult.get("token"),
						user.getId(),user.getEmail(), user.getPhoneNumber(), user.getName(), user.getPic(), user.getRating(),user.isUserVerified());
				result.put(HollerConstants.STATUS, HollerConstants.SUCCESS);
				result.put(HollerConstants.RESULT, signUpResponseDTO);
			}else{
				log.info("loginUser :: user with email {} not found", loginDTO.getEmail());
				result.put(HollerConstants.STATUS, HollerConstants.FAILURE);
				result.put(HollerConstants.MESSAGE, HollerConstants.USER_NOT_FOUND);
			}
		} catch (Exception e) {
			log.error("Error in login user", e);
			result.put(HollerConstants.STATUS, HollerConstants.FAILURE);
			result.put(HollerConstants.MESSAGE, HollerConstants.LOGIN_FAILURE);
		}
		return result;
	}

	public Map<String, Object> loginUserWithPhoneNumber(LoginDTO loginDTO, HttpServletRequest request) {
		log.info("loginUser :: called");
		Map<String, Object> result = new HashMap<String, Object>();
		boolean isValidOtp = otpService.validateOtp(loginDTO.getPhoneNumber(), loginDTO.getOtp());
		if(!isValidOtp){
			//if(!Boolean.TRUE){
			log.info("loginUser :: otp entered is invalid");
			result.put(HollerConstants.STATUS, HollerConstants.FAILURE);
			result.put(HollerConstants.MESSAGE, HollerConstants.OTP_SIGNUP_FAILURE);
			return result;
		}
		try {
			log.info("loginUser :: otp entered is valid");
			log.info("loginUser :: try to login user with phoneNumber {}", loginDTO.getPhoneNumber());
			if(isUserPresentWithPhoneNumber(loginDTO.getPhoneNumber())){
				User user = userDao.getByPhoneNumber(loginDTO.getPhoneNumber());
				if(user != null){
					Map<String, Object> tokenResult = tokenService.generateTokenWithPhoneNumber(loginDTO.getPhoneNumber());
					SignUpResponseDTO signUpResponseDTO = new SignUpResponseDTO((String)tokenResult.get("token"),
							user.getId(),user.getEmail(), user.getPhoneNumber(), user.getName(), user.getPic(), user.getRating(),user.isUserVerified());
					result.put(HollerConstants.STATUS, HollerConstants.SUCCESS);
					result.put(HollerConstants.RESULT, signUpResponseDTO);
				}else{
					log.info("loginUser :: user with phoneNumber {} not found", loginDTO.getPhoneNumber());
					result.put(HollerConstants.STATUS, HollerConstants.FAILURE);
					result.put(HollerConstants.MESSAGE, HollerConstants.USER_NOT_FOUND);
				}
			}else{
				log.info("signUpUser :: creating new user with name " + loginDTO.getName() + " and phoneNumber " + loginDTO.getPhoneNumber());
				String name = loginDTO.getName().substring(0,1).toUpperCase() + loginDTO.getName().substring(1);
				User user = User.constructUserForSignUp(name, null, loginDTO.getPhoneNumber(), HollerConstants.PLATFORM_HOLLER);
				UserDetails ud = new UserDetails();
				user.setUserDetails(ud);
				userDao.save(user);

				Map<String, Object> tokenResult = tokenService.generateTokenWithPhoneNumber(loginDTO.getPhoneNumber());
				SignUpResponseDTO signUpResponseDTO = new SignUpResponseDTO((String)tokenResult.get("token"),
						user.getId(),user.getEmail(), user.getPhoneNumber(), user.getName(), user.getPic());

				result.put(HollerConstants.STATUS, HollerConstants.SUCCESS);
				result.put(HollerConstants.RESULT, signUpResponseDTO);
				smsSender.sendWelcomeMsgSMS(loginDTO.getPhoneNumber(), name);
			}


		} catch (Exception e) {
			log.error("Error in login user", e);
			result.put(HollerConstants.STATUS, HollerConstants.FAILURE);
			result.put(HollerConstants.MESSAGE, HollerConstants.LOGIN_FAILURE);
		}
		return result;
	}
	
	public Map<String, Object> updateUserDeviceInfo(UserDeviceInfoDTO deviceInfoDTO, HttpServletRequest request) {
		log.info("updateUserDeviceInfo :: called");
		Map<String, Object> result = new HashMap<String, Object>();
		if(tokenService.isValidToken(request)){
		//if(Boolean.TRUE){
			log.info("updateUserDeviceInfo :: valid token");
			log.info("updateUserDeviceInfo :: for user {}", deviceInfoDTO.getUserId());
			User user = userDao.findById(deviceInfoDTO.getUserId());
			user.setHashedDevice(deviceInfoDTO.getHashedDeviceId());
			userDao.update(user);
			result.put(HollerConstants.STATUS, HollerConstants.SUCCESS);
			result.put(HollerConstants.RESULT, Boolean.TRUE);
		 }else{
			result.put(HollerConstants.STATUS, HollerConstants.FAILURE);
			result.put(HollerConstants.MESSAGE, HollerConstants.TOKEN_VALIDATION_FAILED);
		}
		return result;
	}

    public Map<String, Object> fetchUserHeader(HttpServletRequest request) {
		log.info("fetchUserHeader :: called");
		Map<String, Object> result = new HashMap<String, Object>();
		if(tokenService.isValidToken(request)) {
			//if(Boolean.TRUE){
			log.info("fetchUserHeader :: valid token");
			log.info("fetchUserHeader :: fetch notification and message count for user {}", request.getHeader("userId"));
			User user = userDao.findById(Integer.valueOf(request.getHeader("userId")));
			Object[] countResult = userDao.fetchNotificationAndMessageCount(user.getId());
			UserHeaderDTO userHeaderDTO = null;
			if(CommonUtil.isNotNull(countResult)){
				userHeaderDTO = new UserHeaderDTO();
				userHeaderDTO.setUnSeenNotificationCount(((BigInteger) countResult[0]).intValue());
				userHeaderDTO.setUnSeenMessageCount(((BigInteger) countResult[1]).intValue());
			}
			result.put(HollerConstants.STATUS, HollerConstants.SUCCESS);
			result.put(HollerConstants.RESULT, userHeaderDTO);
		}else {
			result.put(HollerConstants.STATUS, HollerConstants.FAILURE);
			result.put(HollerConstants.MESSAGE, HollerConstants.TOKEN_VALIDATION_FAILED);
		}
		return result;
    }
}
