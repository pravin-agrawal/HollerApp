package com.holler.holler_service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.holler.bean.*;
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

	@Autowired
	UserDao userDao;
	
	@Autowired
	TagDao tagDao;
	
	public boolean authenticateUser(String email, String password){
		return userDao.authenticateUser(email, password);
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
	
	public Map<String, String> signUpUser(SignUpDTO signUpDTO, HttpServletRequest request) {
		Map<String, String> result = new HashMap<String, String>();
		try {
			if(isUserPresent(signUpDTO.getEmail(), signUpDTO.getPhoneNumber())){
				result.put(HollerConstants.FAILURE, HollerConstants.DUPLICATE_USER);
			}else{
				User user = User.constructUserForSignUp(signUpDTO.getName(), signUpDTO.getEmail(), signUpDTO.getPhoneNumber());
				userDao.save(user);
				HttpSession session = request.getSession();
				session.setAttribute("user", user);
				result.put(HollerConstants.SUCCESS, HollerConstants.SIGNUP_SUCCESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put(HollerConstants.FAILURE, HollerConstants.SIGNUP_FAILURE);
		}
		return result;
	}

	public UserDTO getUserProfile(int userId, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session == null){
			return null;
		}else{
			User user = userDao.findByIdWithTags(userId);
			UserDTO userDTO = UserDTO.getDtoForUserProfile(user);
			return userDTO;
		}
	}
	
	public UserDTO updateUserProfile(UserDTO userDTO, HttpServletRequest request){
		HttpSession session = request.getSession(false);
		User loggedInUser = (User) session.getAttribute("user");
		if(session == null && userDTO.getUserId() != loggedInUser.getId()){
			return null;
		}else {
			User user = userDao.findById(userDTO.getUserId());
			UserDTO.setUserDataToUpdate(userDTO, user);
			Set<Tags> tags = new HashSet<Tags>(tagDao.findbyIds(userDTO.getTags()));
			user.setTags(tags);
			userDao.update(user);
			setTagsMapInUserDto(tags, userDTO);
			return userDTO;
		}
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

	public Map<String, Object> updateUserCurrentLocationAndAddress(UserLocationDTO userLocationDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		User user = userDao.findById(userLocationDTO.getUserId());
		String currentLocation = UserLocationDTO.getLocationInCommaSeparatedString(userLocationDTO);
		String currentAddress = UserLocationDTO.getAddressFromLocation(userLocationDTO);

		user.setCurrentLocation(currentLocation);
		user.setCurrentAddress(currentAddress);
		userDao.update(user);
		result.put(HollerConstants.SUCCESS, HollerConstants.LOCATION_UPDATED_SUCCESSFULLY);
		return result;
	}

}
