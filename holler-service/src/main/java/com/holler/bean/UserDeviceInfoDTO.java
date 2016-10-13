package com.holler.bean;

public class UserDeviceInfoDTO {
	private Integer userId;
	private String hashedDeviceId;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getHashedDeviceId() {
		return hashedDeviceId;
	}
	public void setHashedDeviceId(String hashedDeviceId) {
		this.hashedDeviceId = hashedDeviceId;
	}
}
