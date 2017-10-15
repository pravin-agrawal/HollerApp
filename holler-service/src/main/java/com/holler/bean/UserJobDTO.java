package com.holler.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.holler.holler_dao.entity.Jobs;
import com.holler.holler_dao.entity.Tags;
import com.holler.holler_dao.entity.User;
import com.holler.holler_dao.entity.enums.JobMedium;
import com.holler.holler_dao.entity.enums.JobStatusType;
import com.holler.holler_dao.entity.enums.JobType;
import com.holler.holler_dao.entity.enums.UserJobStatusType;
import com.holler.holler_dao.util.AddressConverter;
import com.holler.holler_dao.util.CommonUtil;

import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Getter @Setter
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
	private Date jobAcceptedDate; //date on which job was accepted
	private String jobAddress;
	private String lat;
	private String lng;
	private List<UserJobStatus> userJobStatusList;
	private float rating;
	private boolean userVerified;
	private String jobMedium;
	private String jobType;




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
		if(userJobDTO.getJobMedium().equals(JobMedium.OFFLINE.name())){
			job.setJobMedium(JobMedium.OFFLINE);
		}else{
			job.setJobMedium(JobMedium.ONLINE);
		}
		if(userJobDTO.getJobType().equals(JobType.PARTTIME.name())){
			job.setJobType(JobType.PARTTIME);
		}else{
			job.setJobType(JobType.FULLTIME);
		}
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
		jobDTO.setLat(job.getJobLocation().split(",")[0]);
		jobDTO.setLng(job.getJobLocation().split(",")[1]);
		Set<Tags> tags = job.getTags();
		Set<Integer> tagIds = new HashSet<Integer>();
		for(Tags tag : CommonUtil.safe(tags)){
			tagIds.add(tag.getId());
		}
		jobDTO.tags = tagIds;
		jobDTO.setJobMedium(job.getJobMedium().name());
		jobDTO.setJobType(job.getJobType().name());
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
			if(CommonUtil.isNotNull(job.getJobMedium())){
				userJobDTO.setJobMedium(job.getJobMedium().name());
			}
			jobDTOs.add(userJobDTO);
		}
		return jobDTOs;
	}

	public static List<UserJobDTO> getPingedJobDtosList(List<Object[]> jobs){
		log.info("getJobDtosToViewJobList :: called");
		List<UserJobDTO> jobDTOs = new ArrayList<UserJobDTO>();
		for (Object[] object : jobs) {
			if(object != null) {
				UserJobDTO userJobDTO = new UserJobDTO();
				userJobDTO.setJobId((Integer) object[0]);
				userJobDTO.setTitle((String) object[1]);
				userJobDTO.setJobDescription((String) object[2]);
				userJobDTO.setCompensation((Integer) object[3]);
				userJobDTO.setJobAcceptedDate((Date) object[4]);
				userJobDTO.setStatus((String) object[5]);
				jobDTOs.add(userJobDTO);
			}
		}
		return jobDTOs;
	}


	public static List<UserJobDTO> getJobIdAndTitleByDiscoveryPreference(List<Jobs> jobs, User user) {
		log.info("getJobIdAndTitleByDiscoveryPreference :: called");
		int jobDiscoveryLimit = user.getUserDetails().getJobDiscoveryLimit();
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
				if(CommonUtil.isNotNull(job.getJobMedium())){
					userJobDTO.setJobMedium(job.getJobMedium().name());
				}
				if(CommonUtil.isNotNull(job.getJobType())){
					userJobDTO.setJobType(job.getJobType().name());
				}
				Set<Tags> tags = job.getTags();
				Set<Integer> tagIds = new HashSet<Integer>();
				for(Tags tag : CommonUtil.safe(tags)){
					tagIds.add(tag.getId());
				}
				userJobDTO.tags = tagIds;
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
					userJobDTO.setUserId((Integer) object[0]);
					userJobDTO.setUserName((String) object[1]);
					userJobDTO.setUserProfilePic((String) object[2]);
					userJobDTO.setStatus((String) object[3]);
					userJobDTO.setRating((Float) object[4]);
					userJobDTO.setUserVerified((Boolean) object[5]);
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
				jobStatus.setAcceptedByUserId((Integer) object[1]);
				jobStatus.setJobStatusType(UserJobStatusType.valueOf((String) object[2]));
				jobStatus.setAcceptedByUsername((String) object[3]);
				jobStatus.setAcceptedByPic((String) object[4]);
				jobStatus.setAccepterAvgRating((Float) object[5]);
				jobStatus.setAccepterVerified((Boolean) object[6]);
				userJobStatusList.add(jobStatus);
			}
		}
		return userJobStatusList;
	}
}
