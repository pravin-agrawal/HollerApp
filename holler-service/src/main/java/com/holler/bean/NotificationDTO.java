package com.holler.bean;


import java.util.ArrayList;
import java.util.List;

public class NotificationDTO {
	private int id;
	private int fromUserId;
	private int toUserId;
	private String notificationType;
	private boolean isRead;
	private boolean isSent;
	private int objectId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFromUserId() {
		return fromUserId;
	}
	public void setFromUserId(int fromUserId) {
		this.fromUserId = fromUserId;
	}
	public int getToUserId() {
		return toUserId;
	}
	public void setToUserId(int toUserId) {
		this.toUserId = toUserId;
	}
	public String getNotificationType() {
		return notificationType;
	}
	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}
	public boolean isRead() {
		return isRead;
	}
	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}
	public boolean isSent() {
		return isSent;
	}
	public void setSent(boolean isSent) {
		this.isSent = isSent;
	}
	public int getObjectId() {
		return objectId;
	}
	public void setObjectId(int objectId) {
		this.objectId = objectId;
	}



	public static List<String> constructNotificationTemplate(List<Object[]> notificationObj){
		List<String> notificationTemplates = new ArrayList<String>();
		if(notificationObj != null && !notificationObj.isEmpty()){
			for (Object[] object : notificationObj) {
				if(object != null){
					String template  = (String)object[6];
					template.replace("FROM_USER",(String)object[4]).replace("JOB_TITLE",(String)object[5]).replace("NOTIFICATION_TYPE",(String) object[1]);
					notificationTemplates.add(template);
				}
			}
		}
		return notificationTemplates;
	}


}
