package com.holler.controller;



import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.holler.bean.SignUpDTO;
import com.holler.holler_service.UserService;


@Controller
public class SignUpController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value="/signUpUser", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> singUp(@RequestBody SignUpDTO signUpDTO, HttpServletRequest request){
		Map<String, Object> result = userService.signUpUser(signUpDTO, request);
		return result;
	}

	
}
