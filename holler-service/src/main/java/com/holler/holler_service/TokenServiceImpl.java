package com.holler.holler_service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.holler.holler_dao.util.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.holler.redis.RedisDAO;
import com.holler.util.TokenGenerator;

@Service
public class TokenServiceImpl implements TokenService{
	
	static final Logger log = LogManager.getLogger(TokenServiceImpl.class.getName());
	
	@Autowired
	private RedisDAO redisDao;

	@Autowired
	UserService userService;
	
	public Map<String, Object> validateToken(HttpServletRequest request){
		log.info("validateToken :: called");
		Map<String, Object> result = new HashMap<String, Object>();
		String email = request.getHeader("email");
		String phoneNumber = request.getHeader("phoneNumber");
		String token = request.getHeader("token");

		log.info("validateToken :: validate token {} for email {}", token, email);
		if(StringUtils.isBlank(token)) {
			result.put("code", "1");
			result.put("message", "Token is null or empty");
			return result;
		}
		
		// 1. Validate Token if exists
		// 2 .If success - Process
		String storedToken = null;
		if(CommonUtil.notNullAndEmpty(email)){
			storedToken = redisDao.get(email);
		}else if(CommonUtil.notNullAndEmpty(phoneNumber)){
			storedToken = redisDao.get(phoneNumber);
		}
		log.info("validateToken :: stored token is {}", storedToken);
		if(token.equals(storedToken)) {
			result.put("code", "0");
			result.put("message", "successfully consumed");
		} else {
			result.put("code", "1");
			result.put("message", "User/Token Invalid");
		}
		return result;
	}
	
	public Map<String, Object> generateToken(String email){
		log.info("generateToken :: called");
		Map<String, Object> result = new HashMap<String, Object>();
		// 1. User Validation against DB
		boolean isValidUser = userService.authenticateUserWithEmail(email);
		log.info("generateToken :: is valid user {}", isValidUser);
		// 2 .If success - Then - GetToken
		if(isValidUser){
			String token = redisDao.get(email);
			if(token == null) {
				token = TokenGenerator.generateToken(email);
				redisDao.setex(email, 7200, token);
			}
			result.put("code", "0");
			result.put("token", token);
		}else{
			result.put("code", "1");
			result.put("message", "Invalid User");
		}
		return result;
	}

	public Map<String, Object> generateTokenWithPhoneNumber(String phoneNumber){
		log.info("generateToken :: called");
		Map<String, Object> result = new HashMap<String, Object>();
		// 1. User Validation against DB
		boolean isValidUser = userService.authenticateUserWithPhoneNumber(phoneNumber);
		log.info("generateToken :: is valid user {}", isValidUser);
		// 2 .If success - Then - GetToken
		if(isValidUser){
			String token = redisDao.get(phoneNumber);
			if(token == null) {
				token = TokenGenerator.generateTokenWithPhoneNumber(phoneNumber);
				redisDao.setex(phoneNumber, 7200, token);
			}
			result.put("code", "0");
			result.put("token", token);
		}else{
			result.put("code", "1");
			result.put("message", "Invalid User");
		}
		return result;
	}

	public Boolean isValidToken(HttpServletRequest request) {
		log.info("isValidToken :: called");
		Map<String, Object> tokenResult = validateToken(request);
		if(tokenResult.get("code").equals("0")){
			log.info("isValidToken :: true");
			return Boolean.TRUE;
		}
		log.info("isValidToken :: false");
		return Boolean.FALSE;
	}

}
