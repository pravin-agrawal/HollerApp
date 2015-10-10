package com.holler.controller;



import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.holler.bean.LoginDTO;
import com.holler.holler_service.UserService;


@Controller
public class LoginLogoutController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value="/signInUser", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> login(@RequestBody LoginDTO loginDTO , HttpServletRequest request){
		Map<String, Object> result = userService.signInUser(loginDTO.getPhoneNumber(), request);
		return result;
	}
	
	@RequestMapping(value="/signOutUser", method=RequestMethod.POST)
	public @ResponseBody Map<String, String> logoutUser(HttpServletRequest request){
		Map<String, String> result = userService.signOutUser(request);
		return result;
	}
	
}
