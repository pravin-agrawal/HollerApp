package com.holler.bean;

import com.holler.holler_dao.entity.Tags;
import com.holler.holler_dao.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TagDTO {
	private Integer tagId;
	private String tagName;
	private String tagImage;

	public String getTagImage() {
		return tagImage;
	}

	public void setTagImage(String tagImage) {
		this.tagImage = tagImage;
	}

	public Integer getTagId() {
		return tagId;
	}

	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	
	public static List<TagDTO> getTagDTOsFromTags(List<Tags> tags) {
		List<TagDTO> tagDTOs = new ArrayList<TagDTO>();
		for (Tags tag : CommonUtil.safe(tags)) {
			TagDTO tagDTO = new TagDTO();
			tagDTO.setTagId(tag.getId());
			tagDTO.setTagName(tag.getTagName());
			tagDTO.setTagImage(tag.getTagImageUrl());
			tagDTOs.add(tagDTO);
		}
		return tagDTOs;
	}
}
