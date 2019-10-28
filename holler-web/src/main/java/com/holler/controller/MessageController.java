package com.holler.controller;


import com.holler.bean.MessageDTO;
import com.holler.holler_service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@Controller
@RequestMapping("/message")
public class MessageController {

    @Autowired
    MessageService messageService;

    @RequestMapping(value = "/fetchAllConversation", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> fetchAllConversationForUser(HttpServletRequest request) {
        return messageService.fetchAllConversationForUser(request);
    }

    @RequestMapping(value = "/fetchForUser", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> fetchConversationForUser(@RequestParam("userId") Integer userId, HttpServletRequest request) {
        return messageService.fetchConversationWithUser(userId, request);
    }

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> sendMessage(@RequestBody MessageDTO messageDTO, HttpServletRequest request) {
        return messageService.sendMessage(messageDTO, request);
    }

}
