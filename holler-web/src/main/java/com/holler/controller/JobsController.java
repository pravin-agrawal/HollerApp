
package com.holler.controller;



import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.holler.holler_dao.entity.enums.JobMedium;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.holler.bean.SearchJobsByTagRequestDTO;
import com.holler.bean.UpdateJobRequestDTO;
import com.holler.bean.UpdateUserJobRequestDTO;
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
	
	@RequestMapping(value="/viewJobNew", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> viewJobNew(HttpServletRequest request){
		return jobService.viewJobNew(request);
	}

	@RequestMapping(value="/myJobs", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> myJobs(HttpServletRequest request){
		return jobService.getMyJobs(request);
	}

	@RequestMapping(value="/getUsersAcceptedJob", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> getUsersAcceptedJob(HttpServletRequest request){
		return jobService.getUsersAcceptedJob(request);
	}

	@RequestMapping(value="/searchJobsByTagName", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> searchJobsByTagName(HttpServletRequest request){
		return jobService.searchJobsByTag(request);
	}

	@RequestMapping(value="/searchJobsByTagNameAndMedium", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> searchJobsByTagNameAndMedium(@RequestParam("medium")JobMedium medium,HttpServletRequest request){
		return jobService.searchJobsByTagAndMedium(request, medium);
	}

	@RequestMapping(value="/searchJobsByTagIds", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> searchJobsByTagIds(@RequestBody SearchJobsByTagRequestDTO tagDTO, HttpServletRequest request){
		return jobService.searchJobsByTagIds(tagDTO, request);
	}

	@RequestMapping(value="/searchJobsForUser", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> searchJobsForUser(HttpServletRequest request){
		return jobService.searchJobsForUser(request);
	}

	@RequestMapping(value="/acceptOrUnAcceptJob", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> acceptOrUnAcceptJob(@RequestBody UpdateUserJobRequestDTO updateUserJobRequestDTO, HttpServletRequest request){
		Map<String, Object> result = jobService.acceptOrUnacceptJob(updateUserJobRequestDTO, request);
		return result;
	}

	@RequestMapping(value="/grantOrUnGrantJob", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> grantOrUnGrantJob(@RequestBody UpdateUserJobRequestDTO updateUserJobRequestDTO,HttpServletRequest request){
		Map<String, Object> result = jobService.grantOrUnGrantJob(updateUserJobRequestDTO, request);
		return result;
	}

	@RequestMapping(value="/myPingedJobs", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> myPingedJobs(HttpServletRequest request){
		return jobService.getMyPingedJobs(request);
	}

	@RequestMapping(value="/postedAndPingedJobIds", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> postedAndPingedJobIds(HttpServletRequest request){
		return jobService.getMyPostedAndPingedJobIds(request);
	}
	
	@RequestMapping(value="/completeUserJob", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> completeUserJob(@RequestBody UpdateUserJobRequestDTO updateUserJobRequestDTO, HttpServletRequest request){
		Map<String, Object> result = jobService.completeUserJob(updateUserJobRequestDTO, request);
		return result;
	}
	
	@RequestMapping(value="/completeJob", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> completeJob(@RequestBody UpdateJobRequestDTO jobRequestDTO, HttpServletRequest request){
		Map<String, Object> result = jobService.completeJob(jobRequestDTO, request);
		return result;
	}
	
	@RequestMapping(value="/cancelJob", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> cancelJob(@RequestBody UpdateJobRequestDTO jobRequestDTO, HttpServletRequest request){
		Map<String, Object> result = jobService.cancelJob(jobRequestDTO, request);
		return result;
	}
}
