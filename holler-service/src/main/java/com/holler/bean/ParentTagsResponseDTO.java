package com.holler.bean;

import java.util.ArrayList;
import java.util.List;

import com.holler.holler_dao.entity.ParentTags;
import com.holler.holler_dao.util.CommonUtil;

public class ParentTagsResponseDTO {
	private int id;
	private String parentTagName;
	private List<TagDTO> childTags;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getParentTagName() {
		return parentTagName;
	}
	public void setParentTagName(String parentTagName) {
		this.parentTagName = parentTagName;
	}
	public List<TagDTO> getChildTags() {
		return childTags;
	}
	public void setChildTags(List<TagDTO> childTags) {
		this.childTags = childTags;
	}
	
	public static List<ParentTagsResponseDTO> getParentTagsDTOsFromParentTags(List<ParentTags> parentTags) {
		List<ParentTagsResponseDTO> parentTagsResponseDTOs = new ArrayList<ParentTagsResponseDTO>();
		for (ParentTags parentTag : CommonUtil.safe(parentTags)) {
			ParentTagsResponseDTO tagDTO = new ParentTagsResponseDTO();
			tagDTO.setId(parentTag.getId());
			tagDTO.setParentTagName(parentTag.getParentTagName());
			tagDTO.setChildTags(TagDTO.getTagDTOsFromTags(parentTag.getChildTags()));
			parentTagsResponseDTOs.add(tagDTO);
		}
		return parentTagsResponseDTOs;
	}
	
}
