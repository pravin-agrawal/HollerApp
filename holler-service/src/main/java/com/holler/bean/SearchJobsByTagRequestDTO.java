package com.holler.bean;

import com.holler.holler_dao.entity.enums.JobMedium;

import java.util.Set;

public class SearchJobsByTagRequestDTO {
	private Integer userId;
	private Set<Integer> tagIds;
	private JobMedium jobMedium;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Set<Integer> getTagIds() {
		return tagIds;
	}

	public void setTagIds(Set<Integer> tagIds) {
		this.tagIds = tagIds;
	}

	public void setJobMedium(JobMedium jobMedium) {
		this.jobMedium = jobMedium;
	}

	public JobMedium getJobMedium() {
		return jobMedium;
	}
}
