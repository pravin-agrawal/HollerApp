package com.holler.holler_dao;

import java.util.List;

import com.holler.holler_dao.entity.Notification;
import com.holler.holler_dao.entity.User;

public interface NotificationDao extends BaseDao<Notification> {

	public boolean authenticateUser(String email, String password);
	
	public List<Object[]> getUserJobs(int requestUserId);
	
	public User getByEmailAndPassword(String email, String password);

	boolean checkIfUserExists(String email, String phoneNumber);

	public User getByPhoneNumber(String phoneNumber);

	public User findByIdWithTags(int userId);

	public Object getUnreadNotificationCount(Integer userId);

	public List<Object[]> findByUserId(int id);
	
	public void markAllNotificationsAsSeen(int userId);
	
	public List<Object[]> findByUserIdAndNotificationId(int userId,  int notificationId);
}
