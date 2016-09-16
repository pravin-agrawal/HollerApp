package com.holler.holler_service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.holler.bean.UpdateUserJobRequestDTO;
import com.holler.bean.UserJobDTO;
import com.holler.bean.UserJobStatus;
import com.holler.holler_dao.JobDao;
import com.holler.holler_dao.TagDao;
import com.holler.holler_dao.UserDao;
import com.holler.holler_dao.common.HollerConstants;
import com.holler.holler_dao.entity.Jobs;
import com.holler.holler_dao.entity.Tags;
import com.holler.holler_dao.entity.User;
import com.holler.holler_dao.entity.enums.NotificationType;
import com.holler.holler_dao.entity.enums.UserJobStatusType;
import com.holler.holler_dao.util.CommonUtil;

@Service
public class JobServiceImpl implements JobService{
	
	static final Logger log = LogManager.getLogger(JobServiceImpl.class.getName());

	@Autowired
	JobDao jobDao;

	@Autowired
	TagDao tagDao;

	@Autowired
	UserDao userDao;

	@Autowired
	NotificationService notificationService;
	
	@Autowired
	TokenService tokenService;
	
	@Transactional
	public Map<String, Object> postJob(UserJobDTO userJobDTO, HttpServletRequest request) {
		log.info("postJob :: called");
		Map<String, Object> result = new HashMap<String, Object>();
		   if(tokenService.isValidToken(request)){
		 //if(Boolean.TRUE){
			log.info("postJob :: valid token");
			Jobs job = UserJobDTO.constructJobToPost(userJobDTO);
			userJobDTO.setJobAddress(job.getJobAddress());
			job.setUser(userDao.findById(userJobDTO.getUserId()));
			Set<Tags> tags = new HashSet<Tags>(tagDao.findbyIds(userJobDTO.getTags()));
			job.setTags(tags);
			if(CommonUtil.isNotNull(userJobDTO.getJobId()) && userJobDTO.getJobId() != 0){
				log.info("postJob :: user {} updates existing job with job id {}", userJobDTO.getUserId(), job.getId());
				jobDao.update(job);
				notificationService.createJobUpdateNotification(userJobDTO.getTags(), userJobDTO.getUserId(), job.getId());
			}else{
				log.info("postJob :: user {} posts new job with job id {}", userJobDTO.getUserId(), job.getId());
				jobDao.save(job);
				notificationService.createJobPostNotification(userJobDTO.getTags(), userJobDTO.getUserId(), job.getId());
				userJobDTO.setJobId(job.getId());
			}
			result.put(HollerConstants.STATUS, HollerConstants.SUCCESS);
			result.put(HollerConstants.RESULT, userJobDTO);
		}else{
			result.put(HollerConstants.STATUS, HollerConstants.FAILURE);
			result.put(HollerConstants.MESSAGE, HollerConstants.TOKEN_VALIDATION_FAILED);
		}
		 return result;
	}

	public Map<String, Object> viewJob(HttpServletRequest request){
		log.info("viewJob :: called");
		Map<String, Object> result = new HashMap<String, Object>();
		if(tokenService.isValidToken(request)){
		//if(Boolean.TRUE){
			log.info("viewJob :: valid token");
			log.info("viewJob :: view job with id {}", request.getHeader("jobId"));
			Jobs job = jobDao.findById(Integer.valueOf(request.getHeader("jobId")));
			UserJobDTO jobDTO = UserJobDTO.getJobDtoFromJob(job);
			result.put(HollerConstants.STATUS, HollerConstants.SUCCESS);
			result.put(HollerConstants.RESULT, jobDTO);
		 }else{
			result.put(HollerConstants.STATUS, HollerConstants.FAILURE);
			result.put(HollerConstants.MESSAGE, HollerConstants.TOKEN_VALIDATION_FAILED);
		}
		return result;
	}
	
	public Map<String, Object> viewJobNew(HttpServletRequest request){
		log.info("viewJob :: called");
		Map<String, Object> result = new HashMap<String, Object>();
		//if(tokenService.isValidToken(request)){
		if(Boolean.TRUE){
			log.info("viewJob :: valid token");
			log.info("viewJob :: view job with id {}", request.getHeader("jobId"));
			Jobs job = jobDao.findById(Integer.valueOf(request.getHeader("jobId")));
			UserJobDTO jobDTO = UserJobDTO.getJobDtoFromJob(job);
			List<Object[]> resultList = jobDao.getUserJobStatus(job.getId());
			List<UserJobStatus> userJobStatusList = UserJobDTO.getUserJobStatusList(resultList);
			jobDTO.setUserJobStatusList(userJobStatusList);
			result.put(HollerConstants.STATUS, HollerConstants.SUCCESS);
			result.put(HollerConstants.RESULT, jobDTO);
		 }else{
			result.put(HollerConstants.STATUS, HollerConstants.FAILURE);
			result.put(HollerConstants.MESSAGE, HollerConstants.TOKEN_VALIDATION_FAILED);
		}
		return result;
	}

	public Map<String, Object> getMyJobs(HttpServletRequest request){
		log.info("getMyJobs :: called");
		Map<String, Object> result = new HashMap<String, Object>();
		 if(tokenService.isValidToken(request)){
		 //if(Boolean.TRUE){
			log.info("getMyJobs :: valid token");
			log.info("getMyJobs :: for user id {}", request.getHeader("userId"));
			List<Jobs> job = jobDao.findAllByUserId(Integer.valueOf(request.getHeader("userId")));
			List<UserJobDTO> jobDTO = UserJobDTO.getJobDtosToViewJobList(job);
			result.put(HollerConstants.STATUS, HollerConstants.SUCCESS);
			result.put(HollerConstants.RESULT, jobDTO);
		 }else{
			result.put(HollerConstants.STATUS, HollerConstants.FAILURE);
			result.put(HollerConstants.MESSAGE, HollerConstants.TOKEN_VALIDATION_FAILED);
		}
		return result;
	}

	public Map<String, Object> getMyPingedJobs(HttpServletRequest request) {
		log.info("getMyPingedJobs :: called");
		Map<String, Object> result = new HashMap<String, Object>();
		if(tokenService.isValidToken(request)){
		 //if(Boolean.TRUE){
			log.info("getMyPingedJobs :: valid token");
			log.info("getMyPingedJobs :: for user id {}", request.getHeader("userId"));
			List<Jobs> job = jobDao.getMyPingedJobs(Integer.valueOf(request.getHeader("userId")));
			List<UserJobDTO> jobDTO = UserJobDTO.getJobDtosToViewJobList(job);
			result.put(HollerConstants.STATUS, HollerConstants.SUCCESS);
			result.put(HollerConstants.RESULT, jobDTO);
		 }else{
			result.put(HollerConstants.STATUS, HollerConstants.FAILURE);
			result.put(HollerConstants.MESSAGE, HollerConstants.TOKEN_VALIDATION_FAILED);
		}
		return result;
	}

	public Map<String, Object> getMyPostedAndPingedJobIds(HttpServletRequest request) {
		log.info("getMyPostedAndPingedJobIds :: called");
		Map<String, Object> result = new HashMap<String, Object>();
		if(tokenService.isValidToken(request)){
		//if(Boolean.TRUE){
			log.info("getMyPostedAndPingedJobIds :: valid token");
			log.info("getMyPostedAndPingedJobIds :: for user id {}", request.getHeader("userId"));
			List<Integer> jobIds = jobDao.getMyPostedAndPingedJobIds(Integer.valueOf(request.getHeader("userId")));
			result.put(HollerConstants.STATUS, HollerConstants.SUCCESS);
			result.put(HollerConstants.RESULT, jobIds);
		}else{
			result.put(HollerConstants.STATUS, HollerConstants.FAILURE);
			result.put(HollerConstants.MESSAGE, HollerConstants.TOKEN_VALIDATION_FAILED);
		}
		return result;
	}


	public Map<String, Object> getUsersAcceptedJob(HttpServletRequest request){
		log.info("getUsersAcceptedJob :: called");
		Map<String, Object> result = new HashMap<String, Object>();
		if(tokenService.isValidToken(request)){
		//if(Boolean.TRUE){
			log.info("getUsersAcceptedJob :: valid token");
			log.info("getUsersAcceptedJob :: for job id {}", request.getHeader("userId"));
			List<Object[]> users = jobDao.getUserAcceptedJobs(Integer.valueOf(request.getHeader("jobId")));
			List<UserJobDTO> userDTOs = UserJobDTO.constructUserDTOsForAcceptedJObs(users);
			result.put(HollerConstants.STATUS, HollerConstants.SUCCESS);
			result.put(HollerConstants.RESULT, userDTOs);
		 }else{
			result.put(HollerConstants.STATUS, HollerConstants.FAILURE);
			result.put(HollerConstants.MESSAGE, HollerConstants.TOKEN_VALIDATION_FAILED);
		}
		return result;
	}

	public Map<String, Object> searchJobsByTag(HttpServletRequest request) {
		log.info("searchJobsByTag :: called");
		Map<String, Object> result = new HashMap<String, Object>();
		if(tokenService.isValidToken(request)){
		 //if(Boolean.TRUE){
			log.info("searchJobsByTag :: valid token");
			log.info("searchJobsByTag :: tag names are {}", request.getHeader("tag"));
			List<Jobs> jobs = jobDao.searchJobsByTag(request.getHeader("tag"));
			List<UserJobDTO> jobDTOs = UserJobDTO.getJobDtosToViewJobList(jobs);
			result.put(HollerConstants.STATUS, HollerConstants.SUCCESS);
			result.put(HollerConstants.RESULT, jobDTOs);
		 }else{
			result.put(HollerConstants.STATUS, HollerConstants.FAILURE);
			result.put(HollerConstants.MESSAGE, HollerConstants.TOKEN_VALIDATION_FAILED);
		}
		return result;
	}

	public Map<String, Object> searchJobsByTagIds(Set<Integer> tagIds, Integer userId, HttpServletRequest request) {
		log.info("searchJobsByTagIds :: called");
		Map<String, Object> result = new HashMap<String, Object>();
		if(tokenService.isValidToken(request)){
		 //if(Boolean.TRUE){
			log.info("searchJobsByTagIds :: valid token");
			log.info("searchJobsByTagIds :: user {} searched for tags {}", userId, tagIds);
			List<Jobs> jobs = jobDao.searchJobsByTagIds(tagIds);
			User loggedInUser = userDao.findById(userId);
			List<UserJobDTO> jobDTOs = UserJobDTO.getJobIdAndTitleByDiscoveryPreference(jobs, loggedInUser);
			result.put(HollerConstants.STATUS, HollerConstants.SUCCESS);
			result.put(HollerConstants.RESULT, jobDTOs);
		 }else{
			result.put(HollerConstants.STATUS, HollerConstants.FAILURE);
			result.put(HollerConstants.MESSAGE, HollerConstants.TOKEN_VALIDATION_FAILED);
		}
		return result;
	}

	@Transactional
	public Map<String, Object> acceptOrUnacceptJob(UpdateUserJobRequestDTO updateUserJobRequestDTO, HttpServletRequest request) {
		log.info("acceptOrUnacceptJob :: called");
		Map<String, Object> result = new HashMap<String, Object>();
		if(tokenService.isValidToken(request)){
		// if(Boolean.TRUE){
			log.info("acceptOrUnacceptJob :: valid token");
			 Integer jobId = updateUserJobRequestDTO.getJobId();
			 Integer userId = updateUserJobRequestDTO.getUserId();
			 UserJobStatusType status = UserJobStatusType.valueOf(updateUserJobRequestDTO.getStatus());
				Jobs job = jobDao.findById(jobId);
				if(UserJobStatusType.ACCEPTED == status){
					jobDao.acceptJob(userId, jobId, status);
					log.info("acceptOrUnacceptJob :: user {} accepted job {}", userId, jobId);
					notificationService.createNotification(userId, job.getUser().getId(), NotificationType.AcceptJob, Boolean.FALSE, Boolean.FALSE, job.getId());
				}else if(UserJobStatusType.UNACCEPT == status){
					jobDao.unAcceptJob(userId, jobId);
					log.info("acceptOrUnacceptJob :: user {} unaccepted job {}", userId, jobId);
					notificationService.createNotification(userId, job.getUser().getId(), NotificationType.UnAcceptJob, Boolean.FALSE, Boolean.FALSE, job.getId());
				}
			result.put(HollerConstants.STATUS, HollerConstants.SUCCESS);
			result.put(HollerConstants.RESULT, Boolean.TRUE);
		 }else{
			result.put(HollerConstants.STATUS, HollerConstants.FAILURE);
			result.put(HollerConstants.MESSAGE, HollerConstants.TOKEN_VALIDATION_FAILED);
		}
		return result;
	}

	@Transactional
	public Map<String, Object> grantOrUnGrantJob(UpdateUserJobRequestDTO updateUserJobRequestDTO, HttpServletRequest request) {
		log.info("grantOrUnGrantJob :: called");
		Map<String, Object> result = new HashMap<String, Object>();
		if(tokenService.isValidToken(request)){
		// if(Boolean.TRUE){
			log.info("grantOrUnGrantJob :: valid token");
			 Integer jobId = updateUserJobRequestDTO.getJobId();
			 Integer userId = updateUserJobRequestDTO.getUserId();
			 UserJobStatusType status = UserJobStatusType.valueOf(updateUserJobRequestDTO.getStatus());
			if(UserJobStatusType.GRANTED == status){
				jobDao.grantOrUnGrantJob(userId, jobId, status);
				log.info("grantOrUnGrantJob :: user {} granted job {}", userId, jobId);
				notificationService.createNotification(userId, userId, NotificationType.GrantJob, Boolean.FALSE, Boolean.FALSE, jobId);
			}else if(UserJobStatusType.UNGRANT == status){
				jobDao.grantOrUnGrantJob(userId, jobId, UserJobStatusType.ACCEPTED);
				log.info("grantOrUnGrantJob :: user {} ungranted job {}", userId, jobId);
				notificationService.createNotification(userId, userId, NotificationType.UnGrantJob, Boolean.FALSE, Boolean.FALSE, jobId);
			}
			result.put(HollerConstants.STATUS, HollerConstants.SUCCESS);
			result.put(HollerConstants.RESULT, Boolean.TRUE);
		 }else{
			result.put(HollerConstants.STATUS, HollerConstants.FAILURE);
			result.put(HollerConstants.MESSAGE, HollerConstants.TOKEN_VALIDATION_FAILED);
		}
		return result;
	}
	
	@Transactional
	public Map<String, Object> completeJob(UpdateUserJobRequestDTO updateUserJobRequestDTO, HttpServletRequest request) {
		log.info("completeJob :: called");
		Map<String, Object> result = new HashMap<String, Object>();
		if(tokenService.isValidToken(request)){
		// if(Boolean.TRUE){
			log.info("completeJob :: valid token");
			 Integer jobId = updateUserJobRequestDTO.getJobId();
			 Integer userId = updateUserJobRequestDTO.getUserId();
			 UserJobStatusType status = UserJobStatusType.valueOf(updateUserJobRequestDTO.getStatus());
				Jobs job = jobDao.findById(jobId);
				if(UserJobStatusType.COMPLETED == status){
					jobDao.completeJob(userId, jobId, status);
					log.info("completeJob :: user {} completed job {}", userId, jobId);
					notificationService.createNotification(userId, job.getUser().getId(), NotificationType.CompleteJob, Boolean.FALSE, Boolean.FALSE, job.getId());
				}
			result.put(HollerConstants.STATUS, HollerConstants.SUCCESS);
			result.put(HollerConstants.RESULT, Boolean.TRUE);
		 }else{
			result.put(HollerConstants.STATUS, HollerConstants.FAILURE);
			result.put(HollerConstants.MESSAGE, HollerConstants.TOKEN_VALIDATION_FAILED);
		}
		return result;
	}
}
