package com.holler.controller;



import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.holler.bean.UserDTO;
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
	public @ResponseBody UserJobDTO postJob(@RequestBody UserJobDTO userJobDTO){
		userJobDTO = jobService.postJob(userJobDTO);
		return userJobDTO;
	}

	@RequestMapping(value="/viewJob", method=RequestMethod.GET)
	public @ResponseBody UserJobDTO viewJob(@RequestParam("jobId") int jobId, HttpServletRequest request){
		UserJobDTO userJobDTO = jobService.viewJob(jobId,request);
		return userJobDTO;
	}

	@RequestMapping(value="/myJobs", method=RequestMethod.GET)
	public @ResponseBody List<UserJobDTO> myJobs(@RequestParam("userId") int userId, HttpServletRequest request){
		List<UserJobDTO> userJobDTOs = jobService.getMyJobs(userId, request);
		return userJobDTOs;
	}

	@RequestMapping(value="/getUsersAcceptedJob", method=RequestMethod.POST)
	public @ResponseBody List<UserDTO> getUsersAcceptedJob(@RequestParam("jobId") int jobId, HttpServletRequest request){
		List<UserDTO> userDTOs = jobService.getUsersAcceptedJob(jobId, request);
		return userDTOs;
	}

	@RequestMapping(value="/searchJobsByTag", method=RequestMethod.POST)
	public @ResponseBody List<UserJobDTO> searchJobsByTag(@RequestParam("tag")String tag, HttpServletRequest request){
		List<UserJobDTO> userJobDTO = jobService.searchJobsByTag(tag, request);
		return userJobDTO;
	}
}
