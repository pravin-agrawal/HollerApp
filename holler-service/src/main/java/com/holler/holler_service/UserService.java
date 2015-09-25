package com.holler.holler_service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.holler.bean.UserJobDTO;
import com.holler.holler_dao.UserDao;
import com.holler.holler_dao.entity.User;

@Service
public class UserService {

	@Autowired
	UserDao userDao;
	
	public boolean authenticateUser(String userName, String password){
		return userDao.authenticateUser(userName, password);
	}
	
	public UserJobDTO getUserJobs(User loggedInUser, int requestUserId){
		UserJobDTO userJobDTO = null;
		//if(loggedInUser != null && loggedInUser.getId() == requestUserId){
		if(true){
			List<Object[]> userJobs = userDao.getUserJobs(requestUserId); 
			List<UserJobDTO> userJobDTOs = UserJobDTO.constructUserJobDTO(userJobs);
			userJobDTO = userJobDTOs.get(0);
		}else{
			userJobDTO = null;
		}
		return userJobDTO;
	}
}
