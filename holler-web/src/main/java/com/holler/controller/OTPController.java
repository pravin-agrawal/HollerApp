package com.holler.controller;



import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.holler.holler_dao.common.HollerConstants;
import com.holler.twilioSms.SmsSender;
import com.twilio.sdk.TwilioRestException;
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

	@Autowired
	SmsSender smsSender;
	
	@RequestMapping(value="/generateOTP", method=RequestMethod.POST, consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Map<String, Object> generateOTP(HttpServletRequest request){
		Map<String, Object> result = otpService.generateOtpAndSaveOnRedis(request);
		if(!result.get(HollerConstants.STATUS).equals(HollerConstants.FAILURE)){
			try {
				smsSender.sendSMS(result);
			} catch (TwilioRestException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@RequestMapping(value="/generateOTPForSignup", method=RequestMethod.POST, consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Map<String, Object> generateOTPForSignup(HttpServletRequest request){
		Map<String, Object> result = otpService.generateOtpAndSaveOnRedisForSignup(request);
		if(!result.get(HollerConstants.STATUS).equals(HollerConstants.FAILURE)){
			try {
				smsSender.sendSMS(result);
			} catch (TwilioRestException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
}
