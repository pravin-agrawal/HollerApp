package com.holler.bean;

import com.holler.holler_dao.entity.User;


/**
 *
 *
 */
public class UserSettingDTO {

	private Integer userId;
	private Integer compensationRangeMin;
	private Integer compensationRangeMax;
	private Integer pushNotification;
	private Integer jobDiscoveryLimit;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getCompensationRangeMax() {
		return compensationRangeMax;
	}

	public void setCompensationRangeMax(Integer compensationRangeMax) {
		this.compensationRangeMax = compensationRangeMax;
	}

	public Integer getCompensationRangeMin() {
		return compensationRangeMin;
	}

	public void setCompensationRangeMin(Integer compensationRangeMin) {
		this.compensationRangeMin = compensationRangeMin;
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
		userSettingDTO.setCompensationRangeMin(user.getUserDetails().getCompensationRangeMin());
		userSettingDTO.setCompensationRangeMax(user.getUserDetails().getCompensationRangeMax());
		userSettingDTO.setPushNotification(user.getUserDetails().getPushNotification());
		userSettingDTO.setJobDiscoveryLimit(user.getUserDetails().getJobDiscoveryLimit());
		return userSettingDTO;
	}
	
}