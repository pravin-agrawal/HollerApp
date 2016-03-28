package com.holler.controller;



import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.holler.holler_service.OTPService;


@Controller
public class OTPController {
	
	@Autowired
	OTPService otpService;
	
	@RequestMapping(value="/generateOTP", method=RequestMethod.POST, consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Map<String, Object> generateOTP(HttpServletRequest request){
		Map<String, Object> result = otpService.generateOtpAndSaveOnRedis(request);
		//send sms service
		return result;
	}
	
}
