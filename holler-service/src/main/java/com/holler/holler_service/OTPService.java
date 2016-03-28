package com.holler.holler_service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;


public interface OTPService {

	Map<String, Object> generateOtpAndSaveOnRedis(HttpServletRequest request);
	
	public boolean validateOtp(String phoneNumber, String otp);
	
}
