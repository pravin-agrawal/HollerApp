package com.holler.holler_service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.holler.bean.NotificationDTO;
import com.holler.holler_dao.entity.enums.NotificationType;

import javax.servlet.http.HttpServletRequest;


public interface NotificationService {
	
	public boolean createNotification(NotificationDTO notificationDTO);
	
	public boolean createNotification(int fromUserId, int toUserId, NotificationType type, boolean isRead, boolean seen, int objectId);
	
	public boolean createJobUpdateNotification(Set<Integer> tags, int fromUserId, int objectId);
	
	public boolean createJobPostNotification(Set<Integer> tags, int fromUserId, int objectId);

	public Map<String, Object> getUnreadNotificationCount(HttpServletRequest request);

	public Map<String, Object> fetchAllNotificationForUser(HttpServletRequest request);

	public boolean createUpdateProfileNotification(int userId);
}
