package com.holler.holler_service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.holler.holler_dao.common.HollerConstants;
import com.holler.holler_dao.util.OTP;
import com.holler.redis.RedisDAO;

@Service
public class OTPServiceImpl implements OTPService{
	
	static final Logger log = LogManager.getLogger(OTPServiceImpl.class.getName());
	
	@Autowired
	private RedisDAO redisDao;

	public Map<String, Object> generateOtpAndSaveOnRedis(HttpServletRequest request) {
		log.info("generateOtpAndSaveOnRedis :: called");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String phoneNumber = request.getHeader("phoneNumber");
			String otp = redisDao.get("OTP_" + phoneNumber);
			if(otp == null) {
				otp = String.valueOf(OTP.getOtp());
				redisDao.setex("OTP_" + phoneNumber, 120, otp);
				log.info("generateOtpAndSaveOnRedis :: otp {} generated for phoneNumber {}", otp, phoneNumber);
			}
			result.put(HollerConstants.STATUS, HollerConstants.SUCCESS);
			result.put(HollerConstants.PHONE_NUMBER, phoneNumber);
			result.put(HollerConstants.OTP, otp);
		} catch (Exception e) {
			result.put(HollerConstants.STATUS, HollerConstants.FAILURE);
			result.put(HollerConstants.MESSAGE, HollerConstants.OTP_GENERATION_FAILURE);
		}
		return result;
	}
	
	public boolean validateOtp(String phoneNumber, String otp) {
		log.info("validateOtp :: called");
		String otpFromRedis = redisDao.get("OTP_" + phoneNumber);
		if(otpFromRedis != null && otpFromRedis.equals(otp)){
			log.info("validateOtp :: otp from redis {} and otp from user {}", otpFromRedis, otp);
			log.info("validateOtp :: valid otp");
			return true;	
		}
		log.info("validateOtp :: invalid otp");
		return false;
	}

}
