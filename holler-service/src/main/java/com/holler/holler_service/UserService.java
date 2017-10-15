package com.holler.holler_service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.holler.bean.*;
import com.holler.holler_dao.entity.User;

public interface UserService {
	
	public boolean authenticateUser(String email, String password);
	
	public boolean authenticateUserWithEmail(String email);

	public UserJobDTO getUserJobs(User loggedInUser, int requestUserId);
	
	public Map<String, Object> signInUser(String phoneNumber, HttpServletRequest request);
	
	public Map<String, String> signOutUser(HttpServletRequest request);
	
	public Map<String, Object> signUpUser(SignUpDTO signUpDTO, HttpServletRequest request);

	public Map<String, Object> getUserProfile(HttpServletRequest request);
	
	public Map<String, Object> updateUserProfile(UserDTO userDTO, HttpServletRequest request);

	public boolean isUserPresent(String email, String phoneNumber);

	List<TagDTO> fetchTagsForUserHomePage(Integer userId);

	Map<String,Object> updateUserCurrentLocationAndAddress(UserLocationDTO userLocationDTO, HttpServletRequest request);
	
	public Map<String, Object> getUserSettings(HttpServletRequest request);

	Map<String,Object> updateUserSetting(UserSettingDTO userSettingRequestDTO, HttpServletRequest request);

	public Map<String, Object> loginUser(LoginDTO loginDTO, HttpServletRequest request);

	public Map<String, Object> loginWithSocialPlatform(LoginWithSocialPlatformDTO loginWithSocialPlatform, HttpServletRequest request);
	
	Map<String,Object> updateUserDeviceInfo(UserDeviceInfoDTO deviceInfoDTO, HttpServletRequest request);

    Map<String,Object> fetchUserHeader(HttpServletRequest request);
}
