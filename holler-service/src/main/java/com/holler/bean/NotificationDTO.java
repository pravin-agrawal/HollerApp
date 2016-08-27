package com.holler.bean;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationDTO {
	private int id;
	private int fromUserId;
	private int toUserId;
	private String notificationType;
	private boolean isRead;
	private boolean isSent;
	private int objectId;
	private String notificationTemplate;
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

	public String getNotificationTemplate() {
		return notificationTemplate;
	}

	public void setNotificationTemplate(String notificationTemplate) {
		this.notificationTemplate = notificationTemplate;
	}

	public static List<NotificationDTO> constructNotificationTemplate(List<Object[]> notificationObj){
		List<NotificationDTO> notificationDTOs = new ArrayList<NotificationDTO>();
		if(notificationObj != null && !notificationObj.isEmpty()){
			for (Object[] object : notificationObj) {
				if(object != null){
					NotificationDTO notificationDTO = new NotificationDTO();
					String template  = (String)object[7];
					if(template != null){
						template  = template.replace("FROM_USER",(String)object[5]).replace("JOB_TITLE",(String)object[6]).replace("NOTIFICATION_TYPE",(String) object[3]);
					}
					notificationDTO.setNotificationTemplate(template);
					notificationDTO.setObjectId((Integer)object[4]);
					notificationDTO.setToUserId((Integer)object[2]);
					notificationDTO.setRead(String.valueOf(object[8]).equals("1"));
					notificationDTOs.add(notificationDTO);
				}
			}
		}
		return notificationDTOs;
	}


}
