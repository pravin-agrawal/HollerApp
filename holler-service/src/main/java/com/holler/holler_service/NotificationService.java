package com.holler.holler_service;

import java.util.Set;

import com.holler.bean.NotificationDTO;
import com.holler.holler_dao.entity.enums.NotificationType;


public interface NotificationService {
	
	public boolean createNotification(NotificationDTO notificationDTO);
	
	public boolean createNotification(int fromUserId, int toUserId, NotificationType type, boolean isRead, boolean isSent, int objectId);
	
	public boolean createJobUpdateNotification(Set<Integer> tags, int fromUserId, int objectId);
	
	public boolean createJobPostNotification(Set<Integer> tags, int fromUserId, int objectId);
	
}
