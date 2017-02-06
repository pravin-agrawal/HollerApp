package com.holler.holler_service;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.holler.bean.SearchJobsByTagRequestDTO;
import com.holler.bean.UpdateJobRequestDTO;
import com.holler.bean.UpdateUserJobRequestDTO;
import com.holler.bean.UserJobDTO;
import com.holler.holler_dao.entity.enums.JobMedium;

public interface JobService {

	public Map<String, Object> postJob(UserJobDTO userJobDTO, HttpServletRequest request);
	public Map<String, Object> viewJob(HttpServletRequest request);
	public Map<String, Object> getMyJobs(HttpServletRequest request);
	public Map<String, Object> getMyPingedJobs(HttpServletRequest request);
	public Map<String, Object> getMyPostedAndPingedJobIds(HttpServletRequest request);
	public Map<String, Object> getUsersAcceptedJob(HttpServletRequest request);
	public Map<String, Object> searchJobsByTag(HttpServletRequest request);
	public Map<String, Object> searchJobsByTagIds(SearchJobsByTagRequestDTO tagDTO, HttpServletRequest request);

	Map<String,Object> acceptOrUnacceptJob(UpdateUserJobRequestDTO updateUserJobRequestDTO, HttpServletRequest request);

	Map<String,Object> grantOrUnGrantJob(UpdateUserJobRequestDTO updateUserJobRequestDTO, HttpServletRequest request);
	public Map<String, Object> viewJobNew(HttpServletRequest request);
	Map<String,Object> completeUserJob(UpdateUserJobRequestDTO updateUserJobRequestDTO, HttpServletRequest request);
	Map<String,Object> completeJob(UpdateJobRequestDTO jobRequestDTO, HttpServletRequest request);
	Map<String,Object> cancelJob(UpdateJobRequestDTO jobRequestDTO, HttpServletRequest request);

	Map<String,Object> searchJobsByTagAndMedium(HttpServletRequest request, JobMedium medium);
}
