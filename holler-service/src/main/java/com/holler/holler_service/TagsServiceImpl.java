package com.holler.holler_service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.holler.bean.ParentTagsResponseDTO;
import com.holler.bean.TagDTO;
import com.holler.holler_dao.ParentTagsDao;
import com.holler.holler_dao.TagDao;
import com.holler.holler_dao.common.HollerConstants;

@Service
public class TagsServiceImpl implements TagsService{

	@Autowired
	ParentTagsDao parentTagsDao;

	@Autowired
	TagDao tagDao;
	
	@Autowired
	TokenService tokenService;
	
	@Transactional
	public Map<String, Object> getAllParentTagsWithChildTags(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(tokenService.isValidToken(request)){
		// if(Boolean.TRUE){
			result.put(HollerConstants.STATUS, HollerConstants.SUCCESS);
			result.put(HollerConstants.RESULT, ParentTagsResponseDTO.getParentTagsDTOsFromParentTags(parentTagsDao.findAll()));
		}else{
			result.put(HollerConstants.STATUS, HollerConstants.FAILURE);
			result.put(HollerConstants.MESSAGE, HollerConstants.TOKEN_VALIDATION_FAILED);
		}
		return result;
	}
	
	@Transactional
	public Map<String, Object> getAllChildTags(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(tokenService.isValidToken(request)){
		//if(Boolean.TRUE){
			result.put(HollerConstants.STATUS, HollerConstants.SUCCESS);
			result.put(HollerConstants.RESULT, TagDTO.getTagDTOsFromTags(tagDao.findAll()));
		}else{
			result.put(HollerConstants.STATUS, HollerConstants.FAILURE);
			result.put(HollerConstants.MESSAGE, HollerConstants.TOKEN_VALIDATION_FAILED);
		}
		return result;
	}
	
}
