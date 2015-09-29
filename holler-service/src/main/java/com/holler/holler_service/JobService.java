package com.holler.holler_service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.holler.bean.UserJobDTO;

public interface JobService {

	public UserJobDTO postJob(UserJobDTO userJobDTO);
	public List<UserJobDTO> searchJobsByTag(String tag, HttpServletRequest request);
}
