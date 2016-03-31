package com.holler.controller;



import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.holler.bean.UserDTO;
import com.holler.holler_dao.entity.enums.UserJobStatusType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.holler.bean.UserJobDTO;
import com.holler.holler_service.JobService;


@Controller
public class JobsController {

	@Autowired
	JobService jobService;

	@RequestMapping(value="/postJob", method=RequestMethod.POST)
	public @ResponseBody  Map<String, Object> postJob(@RequestBody UserJobDTO userJobDTO, HttpServletRequest request){
		return jobService.postJob(userJobDTO, request);
	}

	@RequestMapping(value="/viewJob", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> viewJob(HttpServletRequest request){
		return jobService.viewJob(request);
	}

	@RequestMapping(value="/myJobs", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> myJobs(HttpServletRequest request){
		return jobService.getMyJobs(request);
	}

	@RequestMapping(value="/getUsersAcceptedJob", method=RequestMethod.POST)
	public @ResponseBody List<UserDTO> getUsersAcceptedJob(@RequestParam("jobId") int jobId, HttpServletRequest request){
		List<UserDTO> userDTOs = jobService.getUsersAcceptedJob(jobId, request);
		return userDTOs;
	}

	@RequestMapping(value="/searchJobsByTagName", method=RequestMethod.POST)
	public @ResponseBody List<UserJobDTO> searchJobsByTagName(@RequestParam("tag")String tag, HttpServletRequest request){
		List<UserJobDTO> userJobDTO = jobService.searchJobsByTag(tag, request);
		return userJobDTO;
	}

	@RequestMapping(value="/searchJobsByTagIds", method=RequestMethod.POST)
	public @ResponseBody List<UserJobDTO> searchJobsByTagIds(@RequestParam("tagIds")Set<Integer> tagIds, HttpServletRequest request){
		List<UserJobDTO> userJobDTO = jobService.searchJobsByTagIds(tagIds, request);
		return userJobDTO;
	}

	@RequestMapping(value="/acceptOrUnAcceptJob", method=RequestMethod.GET)
	public @ResponseBody Map<String, Object> acceptOrUnAcceptJob(@RequestParam("jobId") int jobId,
			@RequestParam("status") UserJobStatusType status,HttpServletRequest request){
		Map<String, Object> result = jobService.acceptOrUnacceptJob(jobId, status, request);
		return result;
	}

	@RequestMapping(value="/grantOrUnGrantJob", method=RequestMethod.GET)
	public @ResponseBody Map<String, Object> grantOrUnGrantJob(@RequestParam("userId") int userId,
			@RequestParam("jobId") int jobId,@RequestParam("status") UserJobStatusType status,HttpServletRequest request){
		Map<String, Object> result = jobService.grantOrUnGrantJob(userId, jobId, status, request);
		return result;
	}

	@RequestMapping(value="/myPingedJobs", method=RequestMethod.GET)
	public @ResponseBody List<UserJobDTO> myPingedJobs(HttpServletRequest request){
		List<UserJobDTO> userJobDTOs = jobService.getMyPingedJobs(request);
		return userJobDTOs;
	}
}
