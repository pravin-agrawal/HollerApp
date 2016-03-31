package com.holler.holler_service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.holler.bean.UserDTO;
import com.holler.bean.UserJobDTO;
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
		Map<String, Object> result = new HashMap<String, Object>();
		//if(tokenService.isValidToken(request)){
		 if(Boolean.TRUE){
			Jobs job = UserJobDTO.constructJobToPost(userJobDTO);
			userJobDTO.setJobAddress(job.getJobAddress());
			job.setUser(userDao.findById(userJobDTO.getUserId()));
			Set<Tags> tags = new HashSet<Tags>(tagDao.findbyIds(userJobDTO.getTags()));
			job.setTags(tags);
			if(CommonUtil.isNotNull(userJobDTO.getJobId()) && userJobDTO.getJobId() != 0){
				jobDao.update(job);
				notificationService.createJobUpdateNotification(userJobDTO.getTags(), userJobDTO.getUserId(), job.getId());
			}else{
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
		
		Map<String, Object> result = new HashMap<String, Object>();
		//if(tokenService.isValidToken(request)){
		 if(Boolean.TRUE){
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

	public Map<String, Object> getMyJobs(HttpServletRequest request){
		
		Map<String, Object> result = new HashMap<String, Object>();
		//if(tokenService.isValidToken(request)){
		 if(Boolean.TRUE){
			List<Jobs> job = jobDao.findAllByUserId(Integer.valueOf(request.getHeader("userId")));
			List<UserJobDTO> jobDTO = UserJobDTO.getJobDtosForMyPostedJobs(job);
			result.put(HollerConstants.STATUS, HollerConstants.SUCCESS);
			result.put(HollerConstants.RESULT, jobDTO);
		 }else{
			result.put(HollerConstants.STATUS, HollerConstants.FAILURE);
			result.put(HollerConstants.MESSAGE, HollerConstants.TOKEN_VALIDATION_FAILED);
		}
		return result;
	}

	public List<UserJobDTO> getMyPingedJobs(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session == null){
			return null;
		}else {
			User loggedInUser = (User) session.getAttribute("user");
			List<Jobs> job = jobDao.getMyPingedJobs(loggedInUser.getId());
			List<UserJobDTO> jobDTO = UserJobDTO.getJobIdAndTitleDtosFromJobs(job);
			return jobDTO;
		}
	}


	public List<UserDTO> getUsersAcceptedJob(int jobId, HttpServletRequest request){

		HttpSession session = request.getSession(false);
		if(session == null){
			return null;
		}else{
			List<User> users = jobDao.getUserAcceptedJobs(jobId);
			List<UserDTO> userDTOs = UserDTO.constructUserDTOsForAcceptedJObs(users);
			return userDTOs;
		}
	}

	public List<UserJobDTO> searchJobsByTag(String tag, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session == null){
			return null;
		}else{
			List<Jobs> jobs = jobDao.searchJobsByTag(tag);
			List<UserJobDTO> jobDTOs = UserJobDTO.getJobDtosFromJobs(jobs);
			return jobDTOs;
		}
	}

	public List<UserJobDTO> searchJobsByTagIds(Set<Integer> tagIds, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session == null){
			return null;
		}else {
			User loggedInUser = (User) session.getAttribute("user");
			List<Jobs> jobs = jobDao.searchJobsByTagIds(tagIds);
			List<UserJobDTO> jobDTOs = UserJobDTO.getJobIdAndTitleByDiscoveryPreference(jobs, loggedInUser);
			return jobDTOs;
		}
	}

	@Transactional
	public Map<String, Object> acceptOrUnacceptJob(int jobId, UserJobStatusType status, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		HttpSession session = request.getSession(false);
		User loggedInUser = (User) session.getAttribute("user");
		if(session == null){
			return null;
		}else {
			Jobs job = jobDao.findById(jobId);
			if(UserJobStatusType.ACCEPTED == status){
				jobDao.acceptJob(loggedInUser.getId(), jobId, status);
				notificationService.createNotification(loggedInUser.getId(), job.getUser().getId(), NotificationType.AcceptJob, Boolean.FALSE, Boolean.FALSE, job.getId());
				result.put(HollerConstants.SUCCESS, HollerConstants.JOB_ACCEPTED_SUCCESSFULLY);
			}else if(UserJobStatusType.UNACCEPT == status){
				jobDao.unAcceptJob(loggedInUser.getId(), jobId);
				notificationService.createNotification(loggedInUser.getId(), job.getUser().getId(), NotificationType.UnAcceptJob, Boolean.FALSE, Boolean.FALSE, job.getId());
				result.put(HollerConstants.SUCCESS, HollerConstants.JOB_UNACCEPTED_SUCCESSFULLY);
			}
			return result;
		}
	}

	@Transactional
	public Map<String, Object> grantOrUnGrantJob(int userId, int jobId, UserJobStatusType status, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		HttpSession session = request.getSession(false);
		User loggedInUser = (User) session.getAttribute("user");
		if(UserJobStatusType.GRANTED == status){
			jobDao.grantOrUnGrantJob(userId, jobId, status);
			notificationService.createNotification(loggedInUser.getId(), userId, NotificationType.GrantJob, Boolean.FALSE, Boolean.FALSE, jobId);
			result.put(HollerConstants.SUCCESS, HollerConstants.JOB_GRANTED_SUCCESSFULLY);
		}else if(UserJobStatusType.UNGRANT == status){
			jobDao.grantOrUnGrantJob(userId, jobId, UserJobStatusType.ACCEPTED);
			notificationService.createNotification(loggedInUser.getId(), userId, NotificationType.UnGrantJob, Boolean.FALSE, Boolean.FALSE, jobId);
			result.put(HollerConstants.SUCCESS, HollerConstants.JOB_UNGRANTED_SUCCESSFULLY);
		}
		return result;
	}
}
