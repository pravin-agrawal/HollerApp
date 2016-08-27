package com.holler.holler_service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.holler.bean.NotificationDTO;
import com.holler.holler_dao.NotificationDao;
import com.holler.holler_dao.UserDao;
import com.holler.holler_dao.common.HollerConstants;
import com.holler.holler_dao.entity.Notification;
import com.holler.holler_dao.entity.User;
import com.holler.holler_dao.entity.enums.NotificationType;

@Service
public class NotificationServiceImpl implements NotificationService{

	static final Logger log = LogManager.getLogger(NotificationServiceImpl.class.getName());
	
	@Autowired
	NotificationDao notificationDao;

	@Autowired
	UserDao userDao;

	@Autowired
	TokenService tokenService;

	public boolean createNotification(NotificationDTO notificationDTO) {
		Notification notification = new Notification();
		User fromUser = userDao.findById(notificationDTO.getFromUserId());
		User toUser = userDao.findById(notificationDTO.getToUserId());
		notification.setFromUser(fromUser);
		notification.setToUser(toUser);
		notification.setType(NotificationType.valueOf(notificationDTO.getNotificationType()));
		notification.setRead(Boolean.FALSE);
		notification.setSent(Boolean.FALSE);
		notification.setObjectId(notificationDTO.getObjectId());
		notificationDao.save(notification);
		return true;
	}

	public boolean createNotification(int fromUserId, int toUserId, NotificationType type, boolean isRead, boolean isSent, int objectId) {
		log.info("createNotification :: called");
		log.info("createNotification :: about to create notification from source user " + fromUserId + " to user " + toUserId + " of type " + type);
		Notification notification = new Notification();
		User fromUser = userDao.findById(fromUserId);
		User toUser = userDao.findById(toUserId);
		notification.setFromUser(fromUser);
		notification.setToUser(toUser);
		notification.setType(type);
		notification.setRead(isRead);
		notification.setSent(isSent);
		notification.setObjectId(objectId);
		notificationDao.save(notification);
		return true;
	}

	public boolean createJobUpdateNotification(Set<Integer> tags, int fromUserId, int objectId) {
		log.info("createNotification :: about to create job update notification from source user " + fromUserId + " for tags " + tags);
		Set<Integer> userIds = userDao.getUserIdsByTagIds(tags);
		log.info("createNotification :: about to create job update notification from source user " + fromUserId + " for tags " + tags + " for users " + userIds);
		for (Integer toUserId : userIds) {
			if(toUserId != fromUserId){
				createNotification(fromUserId, toUserId, NotificationType.UpdateJob, Boolean.FALSE, Boolean.FALSE, objectId);	
			}
		}
		return true;
	}

	public boolean createJobPostNotification(Set<Integer> tags, int fromUserId, int objectId) {
		log.info("createNotification :: about to create job post notification from source user " + fromUserId + " for tags " + tags);
		Set<Integer> userIds = userDao.getUserIdsByTagIds(tags);
		log.info("createNotification :: about to create job post notification from source user " + fromUserId + " for tags " + tags + " for users " + userIds);
		for (Integer toUserId : userIds) {
			if(toUserId != fromUserId){
				createNotification(fromUserId, toUserId, NotificationType.PostJob, Boolean.FALSE, Boolean.FALSE, objectId);	
			}
		}
		return true;
	}

	public Map<String, Object> getUnreadNotificationCount(HttpServletRequest request) {
		log.info("getUnreadNotificationCount :: called");
		Map<String, Object> result = new HashMap<String, Object>();
		if(tokenService.isValidToken(request)) {
		//if(Boolean.TRUE){
			log.info("getUnreadNotificationCount :: valid token");
			log.info("getUnreadNotificationCount :: fetch notification count for user {}", request.getHeader("userId"));
			User user = userDao.findById(Integer.valueOf(request.getHeader("userId")));
			Object notificationCountResult = notificationDao.getUnreadNotificationCount(user.getId());
			Integer notificationCount = 0;
			if(notificationCountResult != null){
				notificationCount = ((BigInteger)notificationCountResult).intValue();
				log.info("getUnreadNotificationCount :: notification count is {} for user {}", notificationCount, request.getHeader("userId"));
			}
			result.put(HollerConstants.STATUS, HollerConstants.SUCCESS);
			result.put(HollerConstants.RESULT, notificationCount);
		}else {
			result.put(HollerConstants.STATUS, HollerConstants.FAILURE);
			result.put(HollerConstants.MESSAGE, HollerConstants.TOKEN_VALIDATION_FAILED);
		}
		return result;
	}

	@Transactional
	public Map<String, Object> fetchNotification(HttpServletRequest request) {
		log.info("fetchNotification :: called");
		Map<String, Object> result = new HashMap<String, Object>();
		if(tokenService.isValidToken(request)) {
		//if(Boolean.TRUE){
			log.info("fetchNotification :: valid token");
			log.info("fetchNotification :: fetch notifications for user {}", request.getHeader("userId"));
			User user = userDao.findById(Integer.valueOf(request.getHeader("userId")));
			List<Object[]> resultList = notificationDao.findByUserId(user.getId());
			List<NotificationDTO> notificationTemplates = NotificationDTO.constructNotificationTemplate(resultList);
			log.info("fetchNotification :: fetched {} notifications for user {}", notificationTemplates.size(), request.getHeader("userId"));
			notificationDao.markAllNotificationsAsRead(user.getId());
			log.info("Marking all notifications to read for user {}", user.getId());
			result.put(HollerConstants.STATUS, HollerConstants.SUCCESS);
			result.put(HollerConstants.RESULT, notificationTemplates);
		}else {
			result.put(HollerConstants.STATUS, HollerConstants.FAILURE);
			result.put(HollerConstants.MESSAGE, HollerConstants.TOKEN_VALIDATION_FAILED);
		}
		return result;
	}
}
