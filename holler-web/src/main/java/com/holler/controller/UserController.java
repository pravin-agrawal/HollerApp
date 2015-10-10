package com.holler.controller;



import javax.servlet.http.HttpServletRequest;

import com.holler.bean.TagDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.holler.bean.UserDTO;
import com.holler.holler_service.UserService;

import java.util.List;


@Controller
public class UserController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value="/getUserProfile", method=RequestMethod.POST)
	public @ResponseBody UserDTO login(@RequestParam("userId")int userId, HttpServletRequest request){
		UserDTO userDTO = userService.getUserProfile(userId, request);
		return userDTO;
	}
	
	@RequestMapping(value="/updateUserProfile", method=RequestMethod.POST)
	public @ResponseBody UserDTO updateUserProfile(@RequestBody UserDTO userDTO, HttpServletRequest request){
		userDTO = userService.updateUserProfile(userDTO, request);
		return userDTO;
	}

	@RequestMapping(value="/fetchTagsForUserHomePage", method=RequestMethod.POST)
	public @ResponseBody List<TagDTO> fetchTagsForUserHomePage(@RequestParam("userId") Integer userId, HttpServletRequest request) {
		List<TagDTO> tagDTOs = userService.fetchTagsForUserHomePage(userId);
		return tagDTOs;
	}
}
