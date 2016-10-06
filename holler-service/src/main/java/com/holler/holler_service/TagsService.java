package com.holler.holler_service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.holler.bean.AddChildTagsDTO;


public interface TagsService {
	
	Map<String, Object> getAllParentTagsWithChildTags(HttpServletRequest request);

	Map<String, Object> getAllChildTags(HttpServletRequest request);
	
	void saveParentTagImageUrl(Integer tagId, String tagUrl);
	
	void saveChildTagImageUrl(Integer tagId, String tagUrl);
	
	Map<String, Object> addChildTagsToParentTag(AddChildTagsDTO addChildTagsDTO);
}
