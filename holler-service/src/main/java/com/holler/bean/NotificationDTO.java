package com.holler.bean;


import java.util.*;

public class NotificationDTO {
	private int id;
	private int fromUserId;
	private String fromUserName;
	private int toUserId;
	private String notificationType;
	private boolean isRead;
	private boolean isSent;
	private int objectId;
	private String notificationTemplate;
	private Date notificationDate;
	private String userPic;
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

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
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

	public Date getNotificationDate() {
		return notificationDate;
	}

	public void setNotificationDate(Date notificationDate) {
		this.notificationDate = notificationDate;
	}

	public String getUserPic() {
		return userPic;
	}

	public void setUserPic(String userPic) {
		this.userPic = userPic;
	}

	public static List<NotificationDTO> constructNotificationTemplate(List<Object[]> notificationObj){
		List<NotificationDTO> notificationDTOs = new ArrayList<NotificationDTO>();
		if(notificationObj != null && !notificationObj.isEmpty()){
			for (Object[] object : notificationObj) {
				if(object != null){
					NotificationDTO notificationDTO = new NotificationDTO();
					notificationDTO.setId((Integer)object[0]);
					notificationDTO.setFromUserId((Integer)object[1]);
					notificationDTO.setFromUserName((String) object[2]);
					notificationDTO.setUserPic((String) object[3]);
					notificationDTO.setToUserId((Integer)object[4]);
					notificationDTO.setNotificationType((String) object[5]);
					String template  = (String)object[9];
					if(template != null){
						template  = template.replace("FROM_USER",(String)object[7]).replace("JOB_TITLE",(String)object[8]).replace("NOTIFICATION_TYPE",(String) object[5]);
					}
					notificationDTO.setNotificationTemplate(template);
					notificationDTO.setObjectId((Integer)object[6]);
					notificationDTO.setRead(String.valueOf(object[10]).equals("1"));
					notificationDTO.setNotificationDate((Date) object[11]);

					notificationDTOs.add(notificationDTO);
				}
			}
		}
		return notificationDTOs;
	}


}
