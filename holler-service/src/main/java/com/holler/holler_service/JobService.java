package com.holler.holler_service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.holler.bean.UserDTO;
import com.holler.bean.UserJobDTO;

public interface JobService {

	public UserJobDTO postJob(UserJobDTO userJobDTO);
	public UserJobDTO viewJob(int jobId, HttpServletRequest request);
	public List<UserJobDTO> getMyJobs(int userId, HttpServletRequest request);
	public List<UserDTO> getUsersAcceptedJob(int jobId, HttpServletRequest request);
	public List<UserJobDTO> searchJobsByTag(String tag, HttpServletRequest request);
}
