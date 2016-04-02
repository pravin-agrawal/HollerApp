package com.holler.bean;

import java.util.ArrayList;
import java.util.List;

import com.holler.holler_dao.entity.Compensation;
import com.holler.holler_dao.util.CommonUtil;

public class CompensationDTO {
	private Integer compensationId;
	private String slab;
	public Integer getCompensationId() {
		return compensationId;
	}
	public void setCompensationId(Integer compensationId) {
		this.compensationId = compensationId;
	}
	public String getSlab() {
		return slab;
	}
	public void setSlab(String slab) {
		this.slab = slab;
	}
	
	public static List<CompensationDTO> getCompensationDTOsFromCompensation(List<Compensation> compensationList) {
		List<CompensationDTO> compensationDTOs = new ArrayList<CompensationDTO>();
		for (Compensation compensation : CommonUtil.safe(compensationList)) {
			CompensationDTO compensationDTO = new CompensationDTO();
			compensationDTO.setCompensationId(compensation.getId());
			compensationDTO.setSlab(compensation.getSlab());
			compensationDTOs.add(compensationDTO);
		}
		return compensationDTOs;
	}
	
}
