package com.holler.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.holler.bean.EmailDTO;
import com.holler.holler_service.EmailService;
import com.holler.holler_service.HollerService;

@Controller
public class HollerController {
	
	@Autowired
	HollerService hollerService;
	
	@Autowired
	EmailService emailService;
	
	@RequestMapping(value="/getCompensationRange", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> getCompensationRange(HttpServletRequest request){
		return hollerService.getCompensationRange(request);
	}

	@RequestMapping(value = "/faqs", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> fetchFaqs() {
		return hollerService.getFaqs();
	}
	
	@RequestMapping(value = "/getEmailSubjects", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getEmailSubjects() {
		return hollerService.getEmailSubjects();
	}
	
	@RequestMapping(value="/sendSupportEmail", method=RequestMethod.POST)
	public @ResponseBody  Map<String, Object> postJob(@RequestBody EmailDTO emailDTO, HttpServletRequest request){
		return emailService.sendEmail(emailDTO, request);
	}
}
