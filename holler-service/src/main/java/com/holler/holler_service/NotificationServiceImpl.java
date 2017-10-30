package com.holler.holler_service;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.holler.holler_dao.entity.enums.JobMedium;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.holler.bean.NotificationDTO;
import com.holler.holler_dao.JobDao;
import com.holler.holler_dao.NotificationDao;
import com.holler.holler_dao.UserDao;
import com.holler.holler_dao.common.HollerConstants;
import com.holler.holler_dao.entity.Jobs;
import com.holler.holler_dao.entity.Notification;
import com.holler.holler_dao.entity.User;
import com.holler.holler_dao.entity.enums.NotificationType;
import com.holler.holler_dao.util.AddressConverter;
import com.holler.holler_dao.util.CommonUtil;
import com.holler.holler_dao.util.HollerProperties;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;


@Service
public class NotificationServiceImpl implements NotificationService{

	static final Logger log = LogManager.getLogger(NotificationServiceImpl.class.getName());
	
	@Autowired
	NotificationDao notificationDao;

	@Autowired
	UserDao userDao;

	@Autowired
	TokenService tokenService;
	
	@Autowired
	JobDao jobDao;

	public boolean createNotification(NotificationDTO notificationDTO) {
		Notification notification = new Notification();
		User fromUser = userDao.findById(notificationDTO.getFromUserId());
		User toUser = userDao.findById(notificationDTO.getToUserId());
		notification.setFromUser(fromUser);
		notification.setToUser(toUser);
		notification.setType(NotificationType.valueOf(notificationDTO.getNotificationType()));
		notification.setRead(Boolean.FALSE);
		notification.setSeen(Boolean.FALSE);
		notification.setObjectId(notificationDTO.getObjectId());
		notificationDao.save(notification);
		return true;
	}

	@Async
	public boolean createNotification(int fromUserId, int toUserId, NotificationType type, boolean isRead, boolean seen, int objectId) {
		log.info("createNotification :: called");
		log.info("createNotification :: about to create notification from source user " + fromUserId + " to user " + toUserId + " of type " + type);
		Notification notification = new Notification();
		User fromUser = userDao.findById(fromUserId);
		User toUser = userDao.findById(toUserId);
		notification.setFromUser(fromUser);
		notification.setToUser(toUser);
		notification.setType(type);
		notification.setRead(isRead);
		notification.setSeen(seen);
		notification.setObjectId(objectId);
		notificationDao.save(notification);
		if(toUser.getUserDetails().getPushNotification().intValue() == 1){
			pushNotification(toUser, notification);	
		}
		return true;
	}

	public void pushNotification(User toUser, Notification notification) {
		String registeredDevice = toUser.getHashedDevice();
		List<String> androidTargets = new ArrayList<String>();
		androidTargets.add(registeredDevice);
		List<NotificationDTO> notificationList = fetchNotification(toUser.getId(), notification.getId());
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put(HollerConstants.TYPE, HollerConstants.NOTIFICATION);
		resultMap.put(HollerConstants.DATA, notificationList.get(0));
		ObjectMapper mapperObj = new ObjectMapper();
		String jsonData = null;
		try {
			jsonData = mapperObj.writeValueAsString(resultMap);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Sender sender = new Sender(HollerProperties.getInstance().getValue("gcm.browser.key"));
		Message message = new Message.Builder()
		.collapseKey(UUID.randomUUID().toString())
		.timeToLive(30)
		.delayWhileIdle(true)
		.addData("message", jsonData)
		.build();
		try {
			MulticastResult result = sender.send(message, androidTargets, 1);
			if (result.getResults() != null) {
				int canonicalRegId = result.getCanonicalIds();
				if (canonicalRegId != 0) {
					log.info("pushNotification :: notification pushed successfully");
				}
			} else {
				int error = result.getFailure();
				log.info("pushNotification :: notification push failed with error - {}", error);
			}
		} catch (Exception e) {
			log.error("pushNotification expection is {}", e);
		}
	}

	@Async
	public boolean createJobUpdateNotification(Set<Integer> tags, int fromUserId, int objectId) {
		log.info("createNotification :: about to create job update notification from source user " + fromUserId + " for tags " + tags);
		Set<Integer> userIds = userDao.getAcceptedUserListByJobId(objectId);
		log.info("createNotification :: about to create job update notification from source user " + fromUserId + " for tags " + tags + " for users " + userIds);
		Jobs job = jobDao.findById(objectId);
		Double[] jobLatLong = job.getLatLongFromJobLocation();
		for (Integer toUserId : CommonUtil.safe(userIds)) {
			if(toUserId != fromUserId && CommonUtil.isNotNull(toUserId)){
				User user = userDao.findById(toUserId);
				int jobDiscoveryLimit = user.getUserDetails().getJobDiscoveryLimit();
				Double[] userLatLong = user.getLatLongFromCurrentLocation();
				Double userAndJobDistance = AddressConverter.calculateDistanceUsingLatLong(userLatLong[0], userLatLong[1], jobLatLong[0], jobLatLong[1]);
				log.info("userAndJobDistance : "+userAndJobDistance);
				if (userAndJobDistance <= jobDiscoveryLimit) {
					log.info("user Job Distance is less than the discovery limit.");
					createNotification(fromUserId, toUserId, NotificationType.UpdateJob, Boolean.FALSE, Boolean.FALSE, objectId);	
				}
			}
		}
		return true;
	}

	@Async
	public boolean createJobPostNotification(Set<Integer> tags, int fromUserId, int objectId) {
		log.info("createNotification :: about to create job post notification from source user " + fromUserId + " for tags " + tags);
		Set<Integer> userIds = userDao.getUserIdsByTagIds(tags);
		log.info("createNotification :: about to create job post notification from source user " + fromUserId + " for tags " + tags + " for users " + userIds);
		Jobs job = jobDao.findById(objectId);
		Double[] jobLatLong = job.getLatLongFromJobLocation();
		for (Integer toUserId : CommonUtil.safe(userIds)) {
			if(toUserId != fromUserId && CommonUtil.isNotNull(toUserId)){
				if(CommonUtil.isNotNull(job.getJobMedium()) && job.getJobMedium().equals(JobMedium.ONLINE)){
					log.info("Posting notification got online job");
					createNotification(fromUserId, toUserId, NotificationType.PostJob, Boolean.FALSE, Boolean.FALSE, objectId);
				}else{
					User user = userDao.findById(toUserId);
					int jobDiscoveryLimit = user.getUserDetails().getJobDiscoveryLimit();
					Double[] userLatLong = user.getLatLongFromCurrentLocation();
					Double userAndJobDistance = AddressConverter.calculateDistanceUsingLatLong(userLatLong[0], userLatLong[1], jobLatLong[0], jobLatLong[1]);
					log.info("userAndJobDistance : " + userAndJobDistance);
					if (userAndJobDistance <= jobDiscoveryLimit) {
						log.info("user Job Distance is less than the discovery limit.");
						createNotification(fromUserId, toUserId, NotificationType.PostJob, Boolean.FALSE, Boolean.FALSE, objectId);
					}
				}
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
	public Map<String, Object> fetchAllNotificationForUser(HttpServletRequest request) {
		log.info("fetchAllNotificationForUser :: called");
		Map<String, Object> result = new HashMap<String, Object>();
		if(tokenService.isValidToken(request)) {
		//if(Boolean.TRUE){
			log.info("fetchAllNotificationForUser :: valid token");
			log.info("fetchAllNotificationForUser :: fetch notifications for user {}", request.getHeader("userId"));
			User user = userDao.findById(Integer.valueOf(request.getHeader("userId")));
			List<Object[]> resultList = notificationDao.findByUserId(user.getId());
			List<NotificationDTO> notificationTemplates = NotificationDTO.constructNotificationTemplate(resultList);
			log.info("fetchAllNotificationForUser :: fetched {} notifications for user {}", notificationTemplates.size(), request.getHeader("userId"));
			notificationDao.markAllNotificationsAsSeen(user.getId());
			log.info("Marking all notifications to read for user {}", user.getId());
			result.put(HollerConstants.STATUS, HollerConstants.SUCCESS);
			result.put(HollerConstants.RESULT, notificationTemplates);
		}else {
			result.put(HollerConstants.STATUS, HollerConstants.FAILURE);
			result.put(HollerConstants.MESSAGE, HollerConstants.TOKEN_VALIDATION_FAILED);
		}
		return result;
	}

	@Transactional
	public List<NotificationDTO> fetchNotification(int userId, int notificationId) {
		log.info("fetchAllNotificationForUser :: called with userId {} and notificationId {}", userId, notificationId);
		List<Object[]> resultList = notificationDao.findByUserIdAndNotificationId(userId, notificationId);
		List<NotificationDTO> notificationTemplates = NotificationDTO.constructNotificationTemplate(resultList);
		/*List<String> notificationList = new ArrayList<String>();
		for(NotificationDTO notificationDTO : CommonUtil.safe(notificationTemplates)){
			notificationList.add(notificationDTO.getNotificationTemplate());
		}*/
		return notificationTemplates;
	}
}
