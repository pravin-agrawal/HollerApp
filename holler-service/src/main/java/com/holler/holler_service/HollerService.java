package com.holler.holler_service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface HollerService {

	Map<String, Object> getCompensationRange(HttpServletRequest request);

	Map<String,Object> getFaqs();
	
	Map<String,Object> getEmailSubjects();

	Map<String,Object> saveEmailIds(String emailId);

	Map<String,Object> getLatestVersion();

}
