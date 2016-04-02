package com.holler.bean;

import com.holler.holler_dao.entity.User;


/**
 *
 *
 */
public class UserSettingDTO {

	private Integer userId;
	private Integer compensationRange;
	private Integer pushNotification;
	private Integer jobDiscoveryLimit;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getCompensationRange() {
		return compensationRange;
	}
	public void setCompensationRange(Integer compensationRange) {
		this.compensationRange = compensationRange;
	}
	public Integer getPushNotification() {
		return pushNotification;
	}
	public void setPushNotification(Integer pushNotification) {
		this.pushNotification = pushNotification;
	}
	public Integer getJobDiscoveryLimit() {
		return jobDiscoveryLimit;
	}
	public void setJobDiscoveryLimit(Integer jobDiscoveryLimit) {
		this.jobDiscoveryLimit = jobDiscoveryLimit;
	}
	public static UserSettingDTO getDtoForUserSetting(User user) {
		UserSettingDTO userSettingDTO = new UserSettingDTO();
		userSettingDTO.setUserId(user.getId());
		userSettingDTO.setCompensationRange(user.getCompensationRange());
		userSettingDTO.setPushNotification(user.getPushNotification());
		userSettingDTO.setJobDiscoveryLimit(user.getJobDiscoveryLimit());
		return userSettingDTO;
	}
	
}