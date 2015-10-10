package com.holler.holler_service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.holler.bean.SignUpDTO;
import com.holler.bean.TagDTO;
import com.holler.bean.UserDTO;
import com.holler.bean.UserJobDTO;
import com.holler.holler_dao.entity.User;

public interface UserService {
	
	public boolean authenticateUser(String email, String password);
	
	public UserJobDTO getUserJobs(User loggedInUser, int requestUserId);
	
	public Map<String, Object> signInUser(String phoneNumber, HttpServletRequest request);
	
	public Map<String, String> signOutUser(HttpServletRequest request);
	
	public Map<String, String> signUpUser(SignUpDTO signUpDTO, HttpServletRequest request);

	public UserDTO getUserProfile(int userId, HttpServletRequest request);
	
	public UserDTO updateUserProfile(UserDTO userDTO, HttpServletRequest request);

	public boolean isUserPresent(String email, String phoneNumber);

	List<TagDTO> fetchTagsForUserHomePage(Integer userId);
}
