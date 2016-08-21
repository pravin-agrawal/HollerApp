package com.holler.holler_service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface HollerService {

	Map<String, Object> getCompensationRange(HttpServletRequest request);

	Map<String,Object> getFaqs();
}
