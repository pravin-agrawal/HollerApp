package com.holler.bean;

import java.util.List;

public class AddChildTagsDTO {
	private Integer parentTagId;
	private List<String> childTags;

	public Integer getParentTagId() {
		return parentTagId;
	}
	public void setParentTagId(Integer parentTagId) {
		this.parentTagId = parentTagId;
	}
	public List<String> getChildTags() {
		return childTags;
	}
	public void setChildTags(List<String> childTags) {
		this.childTags = childTags;
	}
}
