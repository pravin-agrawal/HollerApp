package com.holler.bean;

import com.holler.holler_dao.entity.enums.UserJobStatusType;

public class UserJobStatus {
	private Integer jobId;
	private Integer acceptedByUserId;
	private UserJobStatusType jobStatusType;
	private String acceptedByUsername;
	private String acceptedByPic;
	private float accepterAvgRating;
	private boolean accepterVerified;

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

	public String getAcceptedByUsername() {
		return acceptedByUsername;
	}

	public void setAcceptedByUsername(String acceptedByUsername) {
		this.acceptedByUsername = acceptedByUsername;
	}

	public String getAcceptedByPic() {
		return acceptedByPic;
	}

	public void setAcceptedByPic(String acceptedByPic) {
		this.acceptedByPic = acceptedByPic;
	}

	public float getAccepterAvgRating() {
		return accepterAvgRating;
	}

	public void setAccepterAvgRating(float accepterAvgRating) {
		this.accepterAvgRating = accepterAvgRating;
	}

	public boolean isAccepterVerified() {
		return accepterVerified;
	}

	public void setAccepterVerified(boolean accepterVerified) {
		this.accepterVerified = accepterVerified;
	}
}
