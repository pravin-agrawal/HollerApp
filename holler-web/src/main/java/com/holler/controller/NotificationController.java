package com.holler.controller;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.holler.holler_service.NotificationService;


@Controller
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @RequestMapping(value = "/getUserUnreadNotificationCount", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> getUserUnreadNotificationCount(HttpServletRequest request) {
        return notificationService.getUnreadNotificationCount(request);
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getNotificationTemplatesForUser( HttpServletRequest request) throws Exception {
        return notificationService.fetchAllNotificationForUser(request);
    }
}
