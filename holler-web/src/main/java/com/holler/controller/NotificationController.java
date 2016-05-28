package com.holler.controller;


import com.holler.bean.NotificationDTO;
import com.holler.holler_service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


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

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> getNotificationTemplatesForUser( HttpServletRequest request) throws Exception {
        return notificationService.fetchNotification(request);
    }
}
