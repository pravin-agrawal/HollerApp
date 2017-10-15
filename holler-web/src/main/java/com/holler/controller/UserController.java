package com.holler.controller;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.holler.bean.UserDTO;
import com.holler.bean.UserDeviceInfoDTO;
import com.holler.bean.UserLocationDTO;
import com.holler.bean.UserSettingDTO;
import com.holler.holler_service.UserService;


@Controller
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/getUserProfile", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> login(HttpServletRequest request) {
        return userService.getUserProfile(request);
    }

    @RequestMapping(value = "/updateUserProfile", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> updateUserProfile(@RequestBody UserDTO userDTO, HttpServletRequest request) {
        return userService.updateUserProfile(userDTO, request);
    }

/*    @RequestMapping(value = "/fetchTagsForUserHomePage", method = RequestMethod.POST)
    public
    @ResponseBody
    List<TagDTO> fetchTagsForUserHomePage(@RequestParam("userId") Integer userId, HttpServletRequest request) {
        List<TagDTO> tagDTOs = userService.fetchTagsForUserHomePage(userId);
        return tagDTOs;
    }*/

    @RequestMapping(value = "/updateUserCurrentLocationAndAddress", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> updateUserCurrentLocationAndAddress(@RequestBody UserLocationDTO userLocationDTO, HttpServletRequest request) {
        Map<String, Object> result = userService.updateUserCurrentLocationAndAddress(userLocationDTO,request);
        return result;
    }
    
    @RequestMapping(value = "/getUserSettings", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> getUserSettings(HttpServletRequest request) {
        return userService.getUserSettings(request);
    }
    
    @RequestMapping(value = "/updateUserSettings", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> updateUserSettings(@RequestBody UserSettingDTO userSettingRequestDTO, HttpServletRequest request) {
        Map<String, Object> result = userService.updateUserSetting(userSettingRequestDTO, request);
        return result;
    }
    
    @RequestMapping(value = "/updateUserDeviceInfo", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> updateUserDeviceInfo(@RequestBody UserDeviceInfoDTO deviceInfoDTO, HttpServletRequest request) {
        Map<String, Object> result = userService.updateUserDeviceInfo(deviceInfoDTO, request);
        return result;
    }

    @RequestMapping(value = "/fetchUserHeader", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> fetchUserHeader(HttpServletRequest request) {
        return userService.fetchUserHeader(request);
    }
}
