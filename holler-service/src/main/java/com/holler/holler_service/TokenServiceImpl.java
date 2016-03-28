package com.holler.holler_service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.holler.redis.RedisDAO;
import com.holler.util.TokenGenerator;

@Service
public class TokenServiceImpl implements TokenService{
	
	@Autowired
	private RedisDAO redisDao;

	@Autowired
	UserService userService;
	
	public Map<String, Object> validateToken(HttpServletRequest request){
		Map<String, Object> result = new HashMap<String, Object>();
		String email = request.getHeader("email");
		String token = request.getHeader("token");

		if(StringUtils.isBlank(token)) {
			result.put("code", "1");
			result.put("message", "Token is null or empty");
			return result;
		}
		
		// 1. Validate Token if exists
		// 2 .If success - Process
		
		String storedToken = redisDao.get(email);
		if(token.equals(storedToken)) {
			result.put("code", "0");
			result.put("message", "successfully consumed");
		} else {
			result.put("code", "1");
			result.put("message", "User/Token Invalid");
		}
		return result;
	}
	
	public Map<String, Object> generateToken(String email, String phoneNumber){
		Map<String, Object> result = new HashMap<String, Object>();
		// 1. User Validation against DB
		boolean isValidUser = userService.authenticateUserWithPhoneNumber(email, phoneNumber);
		// 2 .If success - Then - GetToken
		if(isValidUser){
			String token = redisDao.get(email);
			if(token == null) {
				token = TokenGenerator.generateToken(email);
				redisDao.setex(email, 60, token);
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
		Map<String, Object> tokenResult = validateToken(request);
		if(tokenResult.get("code").equals("0")){
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

}
