package com.holler.holler_service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.holler.bean.UserDTO;
import com.holler.bean.UserJobDTO;
import com.holler.holler_dao.entity.enums.UserJobStatusType;

public interface JobService {

	public UserJobDTO postJob(UserJobDTO userJobDTO);
	public UserJobDTO viewJob(int jobId, HttpServletRequest request);
	public List<UserJobDTO> getMyJobs(int userId, HttpServletRequest request);
	public List<UserJobDTO> getMyPingedJobs(HttpServletRequest request);
	public List<UserDTO> getUsersAcceptedJob(int jobId, HttpServletRequest request);
	public List<UserJobDTO> searchJobsByTag(String tag, HttpServletRequest request);
	public List<UserJobDTO> searchJobsByTagIds(Set<Integer> tagIds, HttpServletRequest request);

	Map<String,Object> acceptOrUnacceptJob(int jobId, UserJobStatusType status, HttpServletRequest request);

	Map<String,Object> grantOrUnGrantJob(int userId, int jobId, UserJobStatusType status, HttpServletRequest request);
}
