package com.holler.holler_service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.holler.bean.FaqsDTO;
import com.holler.holler_dao.FaqDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.holler.bean.CompensationDTO;
import com.holler.holler_dao.CompensationDao;
import com.holler.holler_dao.common.HollerConstants;

@Service
public class HollerServiceImpl implements HollerService{
	static final Logger log = LogManager.getLogger(HollerServiceImpl.class.getName());
	@Autowired
	CompensationDao compensationDao;

	@Autowired
	FaqDao faqDao;

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

}
