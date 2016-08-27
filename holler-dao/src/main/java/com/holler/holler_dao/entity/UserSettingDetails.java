package com.holler.holler_dao.entity;

import com.holler.holler_dao.entity.enums.UserStatusType;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user_setting_details")
public class UserSettingDetails extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5464369837316338488L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "job_discovery_limit")
	private Integer jobDiscoveryLimit;
	
	@Column(name = "compensation_range_min")
	private Integer compensationRangeMin;

	@Column(name = "compensation_range_max")
	private Integer compensationRangeMax;

	@Column(name = "push_notification")
	private boolean pushNotification;

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

	public boolean isPushNotification() {
		return pushNotification;
	}

	public void setPushNotification(boolean pushNotification) {
		this.pushNotification = pushNotification;
	}
}
