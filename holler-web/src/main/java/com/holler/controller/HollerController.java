package com.holler.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.holler.holler_service.HollerService;

@Controller
public class HollerController {
	
	@Autowired
	HollerService hollerService;
	
	@RequestMapping(value="/getCompensationRange", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> getCompensationRange(HttpServletRequest request){
		return hollerService.getCompensationRange(request);
	}

	@RequestMapping(value = "/faqs", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> fetchFaqs() {
		return hollerService.getFaqs();
	}
}
