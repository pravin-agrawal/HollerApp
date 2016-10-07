package com.holler.holler_service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.holler.bean.EmailDTO;

public interface EmailService {

	Map<String, Object> sendEmail(EmailDTO emailDTO, HttpServletRequest request);
}
