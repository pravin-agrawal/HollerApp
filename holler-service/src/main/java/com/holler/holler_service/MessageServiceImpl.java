package com.holler.holler_service;

import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;
import com.holler.bean.MessageDTO;
import com.holler.holler_dao.UserDao;
import com.holler.holler_dao.common.HollerConstants;
import com.holler.holler_dao.entity.Message;
import com.holler.holler_dao.entity.User;
import com.holler.holler_dao.util.HollerProperties;
import com.holler.holler_dao.util.MessageDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by pravina on 01/10/17.
 */
@Service
public class MessageServiceImpl implements MessageService {

    static final Logger log = LogManager.getLogger(MessageServiceImpl.class.getName());

    @Autowired
    TokenService tokenService;

    @Autowired
    MessageDao messageDao;

    @Autowired
    UserDao userDao;

    public Map<String, Object> fetchAllConversationForUser(HttpServletRequest request) {
        log.info("fetchAllConversationForUser :: called");
        Map<String, Object> result = new HashMap<String, Object>();
        if(tokenService.isValidToken(request)){
        //if (Boolean.TRUE) {
            log.info("fetchAllConversationForUser :: valid token");
            List<Object[]> messages = messageDao.fetchAllConversationForUser(Integer.parseInt(request.getHeader("userId")));
            List<MessageDTO> messageDTOs = MessageDTO.constructMessageDTOs(messages);
            result.put(HollerConstants.STATUS, HollerConstants.SUCCESS);
            result.put(HollerConstants.RESULT, messageDTOs);
            return result;
        } else {
            result.put(HollerConstants.STATUS, HollerConstants.FAILURE);
            result.put(HollerConstants.MESSAGE, HollerConstants.TOKEN_VALIDATION_FAILED);
        }
        return null;
    }

    public Map<String, Object> fetchConversationWithUser(Integer toUserId, HttpServletRequest request) {
        log.info("fetchConversationWithUser :: called");
        Map<String, Object> result = new HashMap<String, Object>();
        if(tokenService.isValidToken(request)){
        //if (Boolean.TRUE) {
            log.info("fetchConversationWithUser :: valid token");
            List<Message> messages = messageDao.fetchConversationWithUser(Integer.parseInt(request.getHeader("userId")), toUserId);
            List<MessageDTO> messageDTOs = MessageDTO.constructMessageDTOForSingleUser(messages);
            result.put(HollerConstants.STATUS, HollerConstants.SUCCESS);
            result.put(HollerConstants.RESULT, messageDTOs);
            return result;
        } else {
            result.put(HollerConstants.STATUS, HollerConstants.FAILURE);
            result.put(HollerConstants.MESSAGE, HollerConstants.TOKEN_VALIDATION_FAILED);
        }
        return null;
    }

    @Transactional
    public Map<String, Object> sendMessage(MessageDTO messageDTO, HttpServletRequest request) {
        log.info("sendMessage :: called");
        Map<String, Object> result = new HashMap<String, Object>();
        //if(tokenService.isValidToken(request)){
        if (Boolean.TRUE) {
            log.info("sendMessage :: valid token");
            Message message = MessageDTO.constructMessageToSave(messageDTO);
            messageDao.save(message);
            User toUser = userDao.findById(message.getToUser());
            pushMessage(toUser, message.getMessage());
            result.put(HollerConstants.STATUS, HollerConstants.SUCCESS);
            result.put(HollerConstants.RESULT, message);
            return result;
        } else {
            result.put(HollerConstants.STATUS, HollerConstants.FAILURE);
            result.put(HollerConstants.MESSAGE, HollerConstants.TOKEN_VALIDATION_FAILED);
        }
        return null;
    }

    public void pushMessage(User toUser, String message) {
        String registeredDevice = toUser.getHashedDevice();
        List<String> androidTargets = new ArrayList<String>();
        androidTargets.add(registeredDevice);
        Sender sender = new Sender(HollerProperties.getInstance().getValue("gcm.browser.key"));
        com.google.android.gcm.server.Message pushMessage = new com.google.android.gcm.server.Message.Builder()
                .collapseKey(UUID.randomUUID().toString())
                .timeToLive(30)
                .delayWhileIdle(true)
                .addData("message", message)
                .build();
        try {
            MulticastResult result = sender.send(pushMessage, androidTargets, 1);
            if (result.getResults() != null) {
                int canonicalRegId = result.getCanonicalIds();
                if (canonicalRegId != 0) {
                    log.info("pushMessage :: message pushed successfully");
                }
            } else {
                int error = result.getFailure();
                log.info("pushMessage :: message push failed with error - {}", error);
            }
        } catch (Exception e) {
            log.error("pushMessage expection is {}", e);
        }
    }
}
