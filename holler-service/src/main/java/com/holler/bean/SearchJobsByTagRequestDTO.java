package com.holler.bean;

import java.util.Set;

public class SearchJobsByTagRequestDTO {
	private Integer userId;
	private Set<Integer> tagIds;

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

	
}
