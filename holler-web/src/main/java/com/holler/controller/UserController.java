package com.holler.controller;


import com.holler.bean.TagDTO;
import com.holler.bean.UserDTO;
import com.holler.bean.UserLocationDTO;
import com.holler.holler_service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


@Controller
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/getUserProfile", method = RequestMethod.POST)
    public
    @ResponseBody
    UserDTO login(@RequestParam("userId") int userId, HttpServletRequest request) {
        UserDTO userDTO = userService.getUserProfile(userId, request);
        return userDTO;
    }

    @RequestMapping(value = "/updateUserProfile", method = RequestMethod.POST)
    public
    @ResponseBody
    UserDTO updateUserProfile(@RequestBody UserDTO userDTO, HttpServletRequest request) {
        userDTO = userService.updateUserProfile(userDTO, request);
        return userDTO;
    }

    @RequestMapping(value = "/fetchTagsForUserHomePage", method = RequestMethod.POST)
    public
    @ResponseBody
    List<TagDTO> fetchTagsForUserHomePage(@RequestParam("userId") Integer userId, HttpServletRequest request) {
        List<TagDTO> tagDTOs = userService.fetchTagsForUserHomePage(userId);
        return tagDTOs;
    }

    @RequestMapping(value = "/updateUserCurrentLocationAndAddress", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> fetchTagsForUserHomePage(@RequestBody UserLocationDTO userLocationDTO, HttpServletRequest request) {
        Map<String, Object> result = userService.updateUserCurrentLocationAndAddress(userLocationDTO);
        return result;
    }
}
