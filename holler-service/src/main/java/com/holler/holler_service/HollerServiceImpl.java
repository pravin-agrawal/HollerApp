package com.holler.holler_service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.holler.bean.CompensationDTO;
import com.holler.holler_dao.CompensationDao;
import com.holler.holler_dao.common.HollerConstants;

@Service
public class HollerServiceImpl implements HollerService{
	
	@Autowired
	CompensationDao compensationDao;
	
	@Autowired
	TokenService tokenService;

	public Map<String, Object> getCompensationRange(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(tokenService.isValidToken(request)){
		//if(Boolean.TRUE){
			result.put(HollerConstants.STATUS, HollerConstants.SUCCESS);
			result.put(HollerConstants.RESULT, CompensationDTO.getCompensationDTOsFromCompensation(compensationDao.findAll()));
		}else{
			result.put(HollerConstants.STATUS, HollerConstants.FAILURE);
			result.put(HollerConstants.MESSAGE, HollerConstants.TOKEN_VALIDATION_FAILED);
		}
		return result;
	}

}
