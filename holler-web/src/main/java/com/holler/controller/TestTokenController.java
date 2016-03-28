package com.holler.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.holler.holler_service.TokenService;
import com.holler.holler_service.UserService;
import com.holler.redis.RedisDAO;

@Controller
public class TestTokenController {
	
	@Autowired
	private RedisDAO redisDao;
	
	@Autowired
	UserService userService;
	
	@Autowired
	TokenService tokenService;
	
	@RequestMapping(value = "/getToken", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Map<String, Object> getToken(
			@RequestParam Map<String, String> map, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// 1. User Validation against DB
		String email = map.get("email");
		String phoneNumber = map.get("phoneNumber");
		
		Map<String, Object> result = tokenService.generateToken(email, phoneNumber);
		return result;
	}

	@RequestMapping(value = "/process", method = RequestMethod.POST, consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Map<String, Object> process(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> result = tokenService.validateToken(request);
		return result;
	}

}
