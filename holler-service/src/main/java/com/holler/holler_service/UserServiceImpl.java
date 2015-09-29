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

import com.holler.bean.SignUpDTO;
import com.holler.bean.UserDTO;
import com.holler.bean.UserJobDTO;
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

	public Map<String, String> signInUser(String email, String password, HttpServletRequest request) {
		Map<String, String> result = new HashMap<String, String>();
		User user = userDao.getByEmailAndPassword(email, password); 
		if(user != null){
			HttpSession session = request.getSession(false);
			session = request.getSession();
			session.setAttribute("user", user);
			result.put(HollerConstants.LOGIN_SUCCESS, HollerConstants.SUCCESS);
		}else{
			result.put(HollerConstants.LOGIN_FAILURE, HollerConstants.INVALID_CREDENTIALS);
		}
		return result;
	}

	public Map<String, String> signOutUser(HttpServletRequest request) {
		Map<String, String> result = new HashMap<String, String>();
		HttpSession session = request.getSession(false);
		if(session != null){
            session.invalidate();
        }
		result.put(HollerConstants.LOGOUT_SUCCESS, HollerConstants.SUCCESS);
		return result;
	}
	
	public Map<String, String> signUpUser(SignUpDTO signUpDTO, HttpServletRequest request) {
		Map<String, String> result = new HashMap<String, String>();
		try {
			User user = User.constructUserForSignUp(signUpDTO.getName(), signUpDTO.getEmail(), signUpDTO.getPassword(), signUpDTO.getPhoneNumber());
			userDao.save(user);
			HttpSession session = request.getSession(false);
			session = request.getSession();
			session.setAttribute("user", user);
			result.put(HollerConstants.SIGNUP_SUCCESS, HollerConstants.SUCCESS);
		} catch (Exception e) {
			result.put(HollerConstants.SIGNUP_FAILURE, HollerConstants.INVALID_CREDENTIALS);
		}
		return result;
	}

	public UserDTO getUserProfile(int userId, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session == null){
			return null;
		}else{
			User user = userDao.findById(userId);
			UserDTO userDTO = UserDTO.getDtoForUserProfile(user);
			return userDTO;
		}
	}
	
	public UserDTO updateUserProfile(UserDTO userDTO, HttpServletRequest request){
		HttpSession session = request.getSession(false);
		if(session == null){
			return null;
		}else{
			User user = UserDTO.constructUserDTO(userDTO);
			Set<Tags> tags = new HashSet<Tags>(tagDao.findbyIds(userDTO.getTags()));
			user.setTags(tags);
			if(CommonUtil.isNotNull(userDTO.getUserId())){
				userDao.update(user);
			}else{
				userDao.save(user);
				userDTO.setUserId(user.getId());
			}
			return userDTO;
		}
	}
	
}
