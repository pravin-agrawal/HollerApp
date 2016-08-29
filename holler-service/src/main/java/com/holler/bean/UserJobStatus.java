package com.holler.bean;

import com.holler.holler_dao.entity.enums.UserJobStatusType;

public class UserJobStatus {
	private Integer jobId;
	private Integer acceptedByUserId;
	private UserJobStatusType jobStatusType;
	public Integer getJobId() {
		return jobId;
	}
	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}
	public Integer getAcceptedByUserId() {
		return acceptedByUserId;
	}
	public void setAcceptedByUserId(Integer acceptedByUserId) {
		this.acceptedByUserId = acceptedByUserId;
	}
	public UserJobStatusType getJobStatusType() {
		return jobStatusType;
	}
	public void setJobStatusType(UserJobStatusType jobStatusType) {
		this.jobStatusType = jobStatusType;
	}
}
