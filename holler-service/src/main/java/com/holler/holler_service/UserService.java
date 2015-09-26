package com.holler.holler_service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.holler.bean.SignUpDTO;
import com.holler.bean.UserDTO;
import com.holler.bean.UserJobDTO;
import com.holler.holler_dao.entity.User;

public interface UserService {
	
	public boolean authenticateUser(String email, String password);
	
	public UserJobDTO getUserJobs(User loggedInUser, int requestUserId);
	
	public Map<String, String> signInUser(String email, String password, HttpServletRequest request);
	
	public Map<String, String> signOutUser(HttpServletRequest request);
	
	public Map<String, String> signUpUser(SignUpDTO signUpDTO, HttpServletRequest request);

	public UserDTO getUserProfile(int userId, HttpServletRequest request);
}
