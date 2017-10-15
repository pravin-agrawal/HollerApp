package com.holler.holler_dao.util;

import com.holler.holler_dao.BaseDao;
import com.holler.holler_dao.entity.Message;

import java.util.List;

/**
 * Created by pravina on 01/10/17.
 */
public interface MessageDao extends BaseDao<Message> {
    public List<Object[]> fetchAllConversationForUser(Integer userId);

    List<Message> fetchConversationWithUser(Integer from_userId, Integer to_userId);

    void markAllMessagesAsSeen(int userId);
}
