package com.holler.holler_dao.entity;

import com.holler.holler_dao.entity.enums.UserStatusType;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user_details")
public class UserDetails extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5464369837316338488L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "job_discovery_limit")
	private Integer jobDiscoveryLimit = 50;
	
	@Column(name = "compensation_range_min")
	private Integer compensationRangeMin = 0;

	@Column(name = "compensation_range_max")
	private Integer compensationRangeMax = 50000;

	@Column(name = "push_notification")
	private Integer pushNotification = 1;

	@Column(name = "facebook_profile")
	private String facebookProfile;

	@Column(name = "linkedin_profile")
	private String linkedinProfile;

	@Column(name = "youtube_link")
	private String youtubeLink;

	@Column(name = "other_link")
	private String otherLink;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getJobDiscoveryLimit() {
		return jobDiscoveryLimit;
	}

	public void setJobDiscoveryLimit(Integer jobDiscoveryLimit) {
		this.jobDiscoveryLimit = jobDiscoveryLimit;
	}

	public Integer getCompensationRangeMin() {
		return compensationRangeMin;
	}

	public void setCompensationRangeMin(Integer compensationRangeMin) {
		this.compensationRangeMin = compensationRangeMin;
	}

	public Integer getCompensationRangeMax() {
		return compensationRangeMax;
	}

	public void setCompensationRangeMax(Integer compensationRangeMax) {
		this.compensationRangeMax = compensationRangeMax;
	}

	public Integer getPushNotification() {
		return pushNotification;
	}

	public void setPushNotification(Integer pushNotification) {
		this.pushNotification = pushNotification;
	}

	public String getFacebookProfile() {
		return facebookProfile;
	}

	public void setFacebookProfile(String facebookProfile) {
		this.facebookProfile = facebookProfile;
	}

	public String getLinkedinProfile() {
		return linkedinProfile;
	}

	public void setLinkedinProfile(String linkedinProfile) {
		this.linkedinProfile = linkedinProfile;
	}

	public String getYoutubeLink() {
		return youtubeLink;
	}

	public void setYoutubeLink(String youtubeLink) {
		this.youtubeLink = youtubeLink;
	}

	public String getOtherLink() {
		return otherLink;
	}

	public void setOtherLink(String otherLink) {
		this.otherLink = otherLink;
	}
}
