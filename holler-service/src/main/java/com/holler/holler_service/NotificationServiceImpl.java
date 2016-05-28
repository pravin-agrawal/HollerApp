package com.holler.holler_service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.holler.holler_dao.common.HollerConstants;

import org.hibernate.loader.custom.Return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.holler.bean.NotificationDTO;
import com.holler.holler_dao.NotificationDao;
import com.holler.holler_dao.UserDao;
import com.holler.holler_dao.entity.Notification;
import com.holler.holler_dao.entity.User;
import com.holler.holler_dao.entity.enums.NotificationType;

import javax.servlet.http.HttpServletRequest;

@Service
public class NotificationServiceImpl implements NotificationService{

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

	public boolean createNotification(int fromUserId, int toUserId,
			NotificationType type, boolean isRead, boolean isSent, int objectId) {
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
		Set<Integer> userIds = userDao.getUserIdsByTagIds(tags);
		for (Integer toUserId : userIds) {
			if(toUserId != fromUserId){
				createNotification(fromUserId, toUserId, NotificationType.UpdateJob, Boolean.FALSE, Boolean.FALSE, objectId);	
			}
		}
		return true;
	}

	public boolean createJobPostNotification(Set<Integer> tags, int fromUserId, int objectId) {
		Set<Integer> userIds = userDao.getUserIdsByTagIds(tags);
		for (Integer toUserId : userIds) {
			if(toUserId != fromUserId){
				createNotification(fromUserId, toUserId, NotificationType.PostJob, Boolean.FALSE, Boolean.FALSE, objectId);	
			}
		}
		return true;
	}

	public Map<String, Object> getUnreadNotificationCount(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(tokenService.isValidToken(request)) {
		//if(Boolean.TRUE){
			User user = userDao.findById(Integer.valueOf(request.getHeader("userId")));
			Object notificationCountResult = notificationDao.getUnreadNotificationCount(user.getId());
			Integer notificationCount = 0;
			if(notificationCountResult != null){
				notificationCount = ((BigInteger)notificationCountResult).intValue();
			}
			result.put(HollerConstants.STATUS, HollerConstants.SUCCESS);
			result.put(HollerConstants.RESULT, notificationCount);
		}else {
			result.put(HollerConstants.STATUS, HollerConstants.FAILURE);
			result.put(HollerConstants.MESSAGE, HollerConstants.TOKEN_VALIDATION_FAILED);
		}
		return result;
	}

	public Map<String, Object> fetchNotification(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(tokenService.isValidToken(request)) {
		//if(Boolean.TRUE){
			User user = userDao.findById(Integer.valueOf(request.getHeader("userId")));
			List<Object[]> resultList = notificationDao.findByUserId(
					user.getId());
			List<String> notificationTemplates = NotificationDTO.constructNotificationTemplate(resultList);
			result.put(HollerConstants.STATUS, HollerConstants.SUCCESS);
			result.put(HollerConstants.RESULT, notificationTemplates);
		}else {
			result.put(HollerConstants.STATUS, HollerConstants.FAILURE);
			result.put(HollerConstants.MESSAGE, HollerConstants.TOKEN_VALIDATION_FAILED);
		}

		return result;

	}

}
