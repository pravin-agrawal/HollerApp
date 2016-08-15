package com.holler.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.holler.holler_dao.common.HollerConstants;
import com.holler.holler_service.TokenService;
import com.holler.holler_service.UserService;
import com.holler.redis.RedisDAO;

@Controller
public class TestTokenController {
	
	static final Logger log = LogManager.getLogger(TestTokenController.class.getName());
	
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
		
		Map<String, Object> result = tokenService.generateToken(email);
		return result;
	}

	@RequestMapping(value = "/process", method = RequestMethod.POST, consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Map<String, Object> process(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> result = tokenService.validateToken(request);
		return result;
	}
	
	@RequestMapping(value = "/testUrl/{test}", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> testPathVariable(@PathVariable String test, HttpServletRequest request) {
    	log.info("test message is - {}", test);
		log.info("system property is - {}", System.getProperty("testProp"));
		Map<String, Object> result = new HashMap<String, Object>();
    	result.put(HollerConstants.RESULT, HollerConstants.SUCCESS);
		result.put(HollerConstants.MESSAGE, test);
    	return result;
    }

}
