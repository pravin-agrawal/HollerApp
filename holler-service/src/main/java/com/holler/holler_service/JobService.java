package com.holler.holler_service;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.holler.bean.UpdateJobRequestDTO;
import com.holler.bean.UpdateUserJobRequestDTO;
import com.holler.bean.UserJobDTO;

public interface JobService {

	public Map<String, Object> postJob(UserJobDTO userJobDTO, HttpServletRequest request);
	public Map<String, Object> viewJob(HttpServletRequest request);
	public Map<String, Object> getMyJobs(HttpServletRequest request);
	public Map<String, Object> getMyPingedJobs(HttpServletRequest request);
	public Map<String, Object> getMyPostedAndPingedJobIds(HttpServletRequest request);
	public Map<String, Object> getUsersAcceptedJob(HttpServletRequest request);
	public Map<String, Object> searchJobsByTag(HttpServletRequest request);
	public Map<String, Object> searchJobsByTagIds(Set<Integer> tagIds, Integer userId, HttpServletRequest request);

	Map<String,Object> acceptOrUnacceptJob(UpdateUserJobRequestDTO updateUserJobRequestDTO, HttpServletRequest request);

	Map<String,Object> grantOrUnGrantJob(UpdateUserJobRequestDTO updateUserJobRequestDTO, HttpServletRequest request);
	public Map<String, Object> viewJobNew(HttpServletRequest request);
	Map<String,Object> completeUserJob(UpdateUserJobRequestDTO updateUserJobRequestDTO, HttpServletRequest request);
	Map<String,Object> completeJob(UpdateJobRequestDTO jobRequestDTO, HttpServletRequest request);
}
