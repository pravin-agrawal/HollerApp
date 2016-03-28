package com.holler.holler_service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;


public interface TagsService {
	
	Map<String, Object> getAllParentTagsWithChildTags(HttpServletRequest request);
}
