package com.holler.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class UserJobDTO {
	private String title;
    private String jobDescription;
    private String status;
    private String tags;
    private String compensation;
    private Date jobTimeStamp;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getJobDescription() {
		return jobDescription;
	}
	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getCompensation() {
		return compensation;
	}
	public void setCompensation(String compensation) {
		this.compensation = compensation;
	}
	public Date getJobTimeStamp() {
		return jobTimeStamp;
	}
	public void setJobTimeStamp(Date jobTimeStamp) {
		this.jobTimeStamp = jobTimeStamp;
	}
	
	public static List<UserJobDTO> constructUserJobDTO(List<Object[]> userJobs){
		List<UserJobDTO> userJobDTOs = new ArrayList<UserJobDTO>();
		if(userJobs != null && !userJobs.isEmpty()){
			for (Object[] object : userJobs) {
				if(object != null){
					UserJobDTO userJobDTO = new UserJobDTO();
					userJobDTO.setTitle((String)object[1]);
					userJobDTO.setJobDescription((String)object[2]);
					userJobDTOs.add(userJobDTO);
				}
			}
		}
		return userJobDTOs;
	}
}
