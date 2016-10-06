package com.holler.controller;



import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.holler.bean.AddChildTagsDTO;
import com.holler.holler_service.TagsService;


@Controller
public class TagsController {

	@Autowired
	TagsService tagsService;

	@RequestMapping(value="/fetchParentTagsWithChildTags", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> fetchParentTagsWithChildTags(HttpServletRequest request){
		return tagsService.getAllParentTagsWithChildTags(request);
	}
	
	@RequestMapping(value="/fetchAllChildTags", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> fetchAllChildTags(HttpServletRequest request){
		return tagsService.getAllChildTags(request);
	}
	
	@RequestMapping(value="/addChildTagsToParentTag", method=RequestMethod.POST)
	public @ResponseBody  Map<String, Object> postJob(@RequestBody AddChildTagsDTO addChildTagsDTO, HttpServletRequest request){
		return tagsService.addChildTagsToParentTag(addChildTagsDTO);
	}
}
