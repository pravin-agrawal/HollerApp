package com.holler.controller;



import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
	
	@RequestMapping(value="/searchJobsByTag", method=RequestMethod.POST)
	public @ResponseBody List<UserJobDTO> searchJobsByTag(@RequestParam("tag")String tag, HttpServletRequest request){
		List<UserJobDTO> userJobDTO = jobService.searchJobsByTag(tag, request);
		return userJobDTO;
	}
}
