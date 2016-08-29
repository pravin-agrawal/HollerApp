package com.holler.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.holler.holler_dao.entity.Jobs;
import com.holler.holler_dao.entity.Tags;
import com.holler.holler_dao.entity.User;
import com.holler.holler_dao.entity.enums.JobStatusType;
import com.holler.holler_dao.entity.enums.UserJobStatusType;
import com.holler.holler_dao.util.AddressConverter;
import com.holler.holler_dao.util.CommonUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class UserJobDTO {
	static final Logger log = LogManager.getLogger(UserJobDTO.class.getName());

	private Integer userId;
	private Integer jobId;
	private String title;
	private String userName;
	private String userProfilePic;
	private String jobDescription;
	private String status;
	private Set<Integer> tags;
	private Integer compensation;
	private Date jobTimeStamp;	//job created date
	private String specialrequirement;
	private int genderRequirement;
	private Date jobdate;	//date on which job should be done
	private String jobAddress;
	private double lat;
	private double lng;
	private List<UserJobStatus> userJobStatusList;

	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getJobId() {
		return jobId;
	}
	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}
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
	public Integer getCompensation() {
		return compensation;
	}
	public void setCompensation(Integer compensation) {
		this.compensation = compensation;
	}
	public Date getJobTimeStamp() {
		return jobTimeStamp;
	}
	public void setJobTimeStamp(Date jobTimeStamp) {
		this.jobTimeStamp = jobTimeStamp;
	}
	public String getSpecialrequirement() {
		return specialrequirement;
	}
	public void setSpecialrequirement(String specialrequirement) {
		this.specialrequirement = specialrequirement;
	}
	public int getGenderRequirement() {
		return genderRequirement;
	}
	public void setGenderRequirement(int genderRequirement) {
		this.genderRequirement = genderRequirement;
	}
	public Set<Integer> getTags() {
		return tags;
	}
	public void setTags(Set<Integer> tags) {
		this.tags = tags;
	}

	public Date getJobdate() {
		return jobdate;
	}

	public void setJobdate(Date jobdate) {
		this.jobdate = jobdate;
	}
	
	public String getJobAddress() {
		return jobAddress;
	}
	public void setJobAddress(String jobAddress) {
		this.jobAddress = jobAddress;
	}
	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserProfilePic() {
		return userProfilePic;
	}
	
	public void setUserProfilePic(String userProfilePic) {
		this.userProfilePic = userProfilePic;
	}
	
	public void setUserJobStatusList(List<UserJobStatus> userJobStatusList) {
		this.userJobStatusList = userJobStatusList;
	}
	
	public List<UserJobStatus> getUserJobStatusList() {
		return userJobStatusList;
	}
	
	public void addUserJobStatus(UserJobStatus jobStatus){
		if(CommonUtil.isNull(userJobStatusList)){
			userJobStatusList = new ArrayList<UserJobStatus>();
		}
		userJobStatusList.add(jobStatus);
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

	public static Jobs constructJobToPost(UserJobDTO userJobDTO) {
		Jobs job = new Jobs();
		if(CommonUtil.isNotNull(userJobDTO.getJobId())){
			job.setId(userJobDTO.getJobId());
		}
		job.setTitle(userJobDTO.getTitle());
		job.setDescription(userJobDTO.getJobDescription());
		job.setCompensation(userJobDTO.getCompensation());
		job.setGenderPreference(userJobDTO.getGenderRequirement());
		job.setSpecialRequirement(userJobDTO.getSpecialrequirement());
		job.setJobDate(userJobDTO.getJobdate());
		job.setStatus(JobStatusType.Active);
		job.setJobLocation(getLocationInCommaSeparatedString(userJobDTO));
		job.setJobAddress(getAddressFromLocation(userJobDTO));
		return job;
	}

	public static UserJobDTO getJobDtoFromJob(Jobs job){
		UserJobDTO jobDTO = new UserJobDTO();
		jobDTO.setUserId(job.getUser().getId());
		jobDTO.setUserName(job.getUser().getName());
		jobDTO.setUserProfilePic(job.getUser().getPic());
		jobDTO.setJobId(job.getId());
		jobDTO.setTitle(job.getTitle());
		jobDTO.setJobDescription(job.getDescription());
		jobDTO.setCompensation(job.getCompensation());
		jobDTO.setSpecialrequirement(job.getSpecialRequirement());
		jobDTO.setGenderRequirement(job.getGenderPreference());
		jobDTO.setJobAddress(job.getJobAddress());
		jobDTO.setJobdate(job.getJobDate());
		jobDTO.setStatus(job.getStatus().name());
		Set<Tags> tags = job.getTags();
		Set<Integer> tagIds = new HashSet<Integer>();
		for(Tags tag : CommonUtil.safe(tags)){
			tagIds.add(tag.getId());
		}
		jobDTO.tags = tagIds;
		return jobDTO;
	}

	public static List<UserJobDTO> getJobIdAndTitleDtosFromJobs(List<Jobs> jobs){
		List<UserJobDTO> jobDTOs = new ArrayList<UserJobDTO>();
		for (Jobs job : CommonUtil.safe(jobs)) {
			UserJobDTO userJobDTO = new UserJobDTO();
			userJobDTO.setJobId(job.getId());
			userJobDTO.setTitle(job.getTitle());
			jobDTOs.add(userJobDTO);
		}
		return jobDTOs;
	}
	
	public static List<UserJobDTO> getJobDtosToViewJobList(List<Jobs> jobs){
		log.info("getJobDtosToViewJobList :: called");
		List<UserJobDTO> jobDTOs = new ArrayList<UserJobDTO>();
		for (Jobs job : CommonUtil.safe(jobs)) {
			UserJobDTO userJobDTO = new UserJobDTO();
			userJobDTO.setJobId(job.getId());
			userJobDTO.setUserId(job.getUser().getId());
			userJobDTO.setTitle(job.getTitle());
			userJobDTO.setCompensation(job.getCompensation());
			userJobDTO.setJobdate(job.getJobDate());
			userJobDTO.setJobTimeStamp(job.getCreated());
			userJobDTO.setJobDescription(job.getDescription());
			userJobDTO.setStatus(job.getStatus().name());
			jobDTOs.add(userJobDTO);
		}
		return jobDTOs;
	}


	public static List<UserJobDTO> getJobIdAndTitleByDiscoveryPreference(List<Jobs> jobs, User user) {
		log.info("getJobIdAndTitleByDiscoveryPreference :: called");
		int jobDiscoveryLimit = user.getJobDiscoveryLimit();
		log.info("jobDiscoveryLimit : "+jobDiscoveryLimit);
		Double[] userLatLong = user.getLatLongFromCurrentLocation();
		log.info("userLatLong : "+userLatLong);
		List<UserJobDTO> jobDTOs = new ArrayList<UserJobDTO>();
		for (Jobs job : CommonUtil.safe(jobs)) {
			Double[] jobLatLong = job.getLatLongFromJobLocation();
			log.info("jobLatLong : "+jobLatLong);
			Double userAndJobDistance = AddressConverter.calculateDistanceUsingLatLong(userLatLong[0], userLatLong[1], jobLatLong[0], jobLatLong[1]);
			log.info("userAndJobDistance : "+userAndJobDistance);
			if (userAndJobDistance <= jobDiscoveryLimit) {
				log.info("user Job Distance is less than the discovery limit.");
				UserJobDTO userJobDTO = new UserJobDTO();
				userJobDTO.setJobId(job.getId());
				userJobDTO.setUserId(job.getUser().getId());
				userJobDTO.setTitle(job.getTitle());
				userJobDTO.setCompensation(job.getCompensation());
				userJobDTO.setJobdate(job.getJobDate());
				userJobDTO.setJobTimeStamp(job.getCreated());
				userJobDTO.setJobDescription(job.getDescription());
				jobDTOs.add(userJobDTO);
			}
		}
		return jobDTOs;
	}

	public static String getLocationInCommaSeparatedString(UserJobDTO userJobDTO) {
		StringBuilder locationBuilder = new StringBuilder();
		locationBuilder.append(String.valueOf(userJobDTO.getLat())).append(",").append(String.valueOf(userJobDTO.getLng()));
		return locationBuilder.toString();
	}

	public static String getAddressFromLocation(UserJobDTO userJobDTO){
		return AddressConverter.getAddressFromLatLong(getLocationInCommaSeparatedString(userJobDTO));
	}

	
	public static List<UserJobDTO> constructUserDTOsForAcceptedJObs(List<Object[]> users) {
		List<UserJobDTO> userJobDTOs = new ArrayList<UserJobDTO>();
		if(users != null && !users.isEmpty()){
			for (Object[] object : users) {
				if(object != null){
					UserJobDTO userJobDTO = new UserJobDTO();
					userJobDTO.setUserId((Integer)object[0]);
					userJobDTO.setUserName((String)object[1]);
					userJobDTO.setUserProfilePic((String)object[2]);
					userJobDTO.setStatus((String)object[3]);
					userJobDTOs.add(userJobDTO);
				}
			}
		}
		return userJobDTOs;
	}
	public static List<UserJobStatus> getUserJobStatusList(List<Object[]> resultList) {
		List<UserJobStatus> userJobStatusList = new ArrayList<UserJobStatus>();
		if(CommonUtil.notNullAndEmpty(resultList)){
			for (Object[] object : resultList) {
				UserJobStatus jobStatus = new UserJobStatus();
				jobStatus.setJobId((Integer)object[0]);
				jobStatus.setAcceptedByUserId((Integer)object[1]);
				jobStatus.setJobStatusType(UserJobStatusType.valueOf((String)object[2]));
				userJobStatusList.add(jobStatus);
			}
		}
		return userJobStatusList;
	}
}
