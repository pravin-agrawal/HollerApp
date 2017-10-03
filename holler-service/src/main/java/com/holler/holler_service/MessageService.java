package com.holler.holler_service;

import com.holler.bean.MessageDTO;
import com.holler.bean.UserJobDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by pravina on 01/10/17.
 */
public interface MessageService {

    public Map<String, Object> fetchAllConversationForUser(HttpServletRequest request);

    Map<String,Object> fetchConversationWithUser(Integer userId, HttpServletRequest request);

    Map<String,Object> sendMessage(MessageDTO messageDTO, HttpServletRequest request);
}
