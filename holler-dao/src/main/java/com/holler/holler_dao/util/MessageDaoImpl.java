package com.holler.holler_dao.util;

import com.holler.holler_dao.BaseDaoImpl;
import com.holler.holler_dao.QueryDao;
import com.holler.holler_dao.common.SQLQueryIds;
import com.holler.holler_dao.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by pravina on 01/10/17.
 */
@Repository
public class MessageDaoImpl extends BaseDaoImpl<Message> implements MessageDao {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    QueryDao queryDao;
    @PersistenceContext
    EntityManager entityManager;

    public MessageDaoImpl() {
        super(Message.class);
    }

    public List<Object[]> fetchAllConversationForUser(Integer userId) {
        String sql = queryDao.getQueryString(SQLQueryIds.GET_ALL_CONVERSATION_FOR_USER);
        Query queryObject = entityManager.createNativeQuery(sql)
                .setParameter("userId", userId);
        List<Object[]> resultList = queryObject.getResultList();
        return resultList;
    }

    public List<Message> fetchConversationWithUser(Integer fromUserId, Integer toUserId) {
        String sql = queryDao.getQueryString(SQLQueryIds.GET_CONVERSATION_FOR_USER);
        Query queryObject = entityManager.createNativeQuery(sql, Message.class)
                .setParameter("fromUserId", fromUserId)
                .setParameter("toUserId", toUserId);
        List<Message> resultList = queryObject.getResultList();
        return resultList;
    }

    public void markAllMessagesAsSeen(int userId) {
        Query query = entityManager.createNativeQuery(queryDao.getQueryString(SQLQueryIds.MARK_ALL_MESSAGES_AS_SEEN));
        query.setParameter("userId", userId);
        query.executeUpdate();
    }
}
