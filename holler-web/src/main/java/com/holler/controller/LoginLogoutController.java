package com.holler.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.holler.bean.LoginDTO;
import com.holler.holler_dao.common.HollerConstants;
import com.holler.holler_service.UserService;
import com.holler.redis.RedisDAO;

@Controller
public class LoginLogoutController {

	@Autowired
	UserService userService;
	
	@Autowired
	private RedisDAO redisDao;

	@RequestMapping(value="/loginUser", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> login(@RequestBody LoginDTO loginDTO, HttpServletRequest request){
		Map<String, Object> result = userService.loginUser(loginDTO, request);
		return result;
	}
	
	/*@RequestMapping(value = "/signInUser", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> login(
			@RequestBody LoginDTO loginDTO, HttpServletRequest request) {
		Map<String, Object> result = userService.signInUser(
				loginDTO.getPhoneNumber(), request);
		return result;
	}*/

	@RequestMapping(value = "/signOutUser", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> logoutUser(
			HttpServletRequest request) {
		Map<String, String> result = userService.signOutUser(request);
		return result;
	}

	@RequestMapping(value = "/testHit", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> testHit(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("testHit", HollerConstants.SUCCESS);
		return result;
	}

	@RequestMapping(value = "/testUrl", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Map<String, Object> testUrl(
			@RequestBody String secretKey, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("testUrl", "successfully consumed");
		return result;
	}

}
