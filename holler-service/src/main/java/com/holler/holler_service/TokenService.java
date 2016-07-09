package com.holler.holler_service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface TokenService {
	
	public Map<String, Object> validateToken(HttpServletRequest request);
	
	public Map<String, Object> generateToken(String email);
	
	public Boolean isValidToken(HttpServletRequest request);
}
