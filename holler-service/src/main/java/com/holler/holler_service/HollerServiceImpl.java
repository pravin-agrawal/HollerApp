package com.holler.holler_service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.holler.bean.FaqsDTO;
import com.holler.holler_dao.EmailDao;
import com.holler.holler_dao.EmailSubjectsDao;
import com.holler.holler_dao.FaqDao;

import com.holler.holler_dao.entity.Emails;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.holler.bean.CompensationDTO;
import com.holler.holler_dao.CompensationDao;
import com.holler.holler_dao.common.HollerConstants;
import com.holler.holler_dao.entity.EmailSubjects;
import com.holler.holler_dao.util.CommonUtil;

@Service
public class HollerServiceImpl implements HollerService{
	static final Logger log = LogManager.getLogger(HollerServiceImpl.class.getName());
	@Autowired
	CompensationDao compensationDao;

	@Autowired
	FaqDao faqDao;
	
	@Autowired
	EmailSubjectsDao emailSubjectsDao;

	@Autowired
	EmailDao emailDao;

	@Autowired
	TokenService tokenService;

	public Map<String, Object> getCompensationRange(HttpServletRequest request) {
		log.info("getCompensationRange :: called");
		Map<String, Object> result = new HashMap<String, Object>();
		if(tokenService.isValidToken(request)){
		//if(Boolean.TRUE){
			 log.info("getCompensationRange :: valid token");
			result.put(HollerConstants.STATUS, HollerConstants.SUCCESS);
			result.put(HollerConstants.RESULT, CompensationDTO.getCompensationDTOsFromCompensation(compensationDao.findAll()));
		}else{
			result.put(HollerConstants.STATUS, HollerConstants.FAILURE);
			result.put(HollerConstants.MESSAGE, HollerConstants.TOKEN_VALIDATION_FAILED);
		}
		return result;
	}

	public Map<String, Object> getFaqs() {
		log.info("getFaqs :: called");
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(HollerConstants.STATUS, HollerConstants.SUCCESS);
		result.put(HollerConstants.RESULT, FaqsDTO.getFaqsDTO(faqDao.findAll()));

		return result;
	}

	public Map<String, Object> getEmailSubjects() {
		log.info("getEmailSubjects :: called");
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(HollerConstants.STATUS, HollerConstants.SUCCESS);
		List<EmailSubjects> emailSubjects = emailSubjectsDao.findAll();
		List<String> subjectList = new ArrayList<String>();
		for(EmailSubjects subjects : CommonUtil.safe(emailSubjects)){
			subjectList.add(subjects.getSubject().trim());
		}
		result.put(HollerConstants.RESULT, subjectList);
		return result;
	}

	public Map<String, Object> saveEmailIds(String emailId) {
		log.info("saveEmailIds :: called");
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(HollerConstants.STATUS, HollerConstants.SUCCESS);
		Emails emails = new Emails();
		emails.setEmaiId(emailId);
		emailDao.save(emails);
		if(CommonUtil.isNotNull(emails.getId())){
			result.put(HollerConstants.RESULT, Boolean.TRUE);
		}else{
			result.put(HollerConstants.RESULT, HollerConstants.FAILURE);
		}
		log.info("saveEmailIds :: exit");
		return result;
	}

}
