package com.holler.bean;


import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter @Setter
public class NotificationDTO {
	private int id;
	private int fromUserId;
	private String fromUserName;
	private int toUserId;
	private String notificationType;
	private boolean isRead;
	private boolean seen;
	private int objectId;
	private String notificationTemplate;
	private Date notificationDate;
	private String userPic;

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
