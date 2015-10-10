package com.holler.holler_service;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.holler.bean.UserDTO;
import com.holler.holler_dao.common.HollerConstants;
import com.holler.holler_dao.entity.User;
import com.holler.holler_dao.entity.enums.UserJobStatusType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.holler.bean.UserJobDTO;
import com.holler.holler_dao.JobDao;
import com.holler.holler_dao.TagDao;
import com.holler.holler_dao.UserDao;
import com.holler.holler_dao.entity.Jobs;
import com.holler.holler_dao.entity.Tags;
import com.holler.holler_dao.util.CommonUtil;

@Service
public class JobServiceImpl implements JobService{

	@Autowired
	JobDao jobDao;

	@Autowired
	TagDao tagDao;

	@Autowired
	UserDao userDao;


	@Transactional
	public UserJobDTO postJob(UserJobDTO userJobDTO) {
		Jobs job = UserJobDTO.constructJobToPost(userJobDTO);
		job.setUser(userDao.findById(userJobDTO.getUserId()));
		Set<Tags> tags = new HashSet<Tags>(tagDao.findbyIds(userJobDTO.getTags()));
		job.setTags(tags);
		if(CommonUtil.isNotNull(userJobDTO.getJobId())){
			jobDao.update(job);
		}else{
			jobDao.save(job);
			userJobDTO.setJobId(job.getId());
		}
		return userJobDTO;
	}

	public UserJobDTO viewJob(int jobId,HttpServletRequest request){
		HttpSession session = request.getSession(false);
		if(session == null){
			return null;
		}else{
			Jobs job = jobDao.findById(jobId);
			UserJobDTO jobDTO = UserJobDTO.getJobDtoFromJob(job);
			return jobDTO;
		}
	}

	public List<UserJobDTO> getMyJobs(int userId, HttpServletRequest request){
		HttpSession session = request.getSession(false);
		if(session == null){
			return null;
		}else{
			List<Jobs> job = jobDao.findAllByUserId(userId);
			List<UserJobDTO> jobDTO = UserJobDTO.getJobIdAndTitleDtosFromJobs(job);
			return jobDTO;
		}
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
		}else{
			List<Jobs> jobs = jobDao.searchJobsByTagIds(tagIds);
			List<UserJobDTO> jobDTOs = UserJobDTO.getJobIdAndTitleDtosFromJobs(jobs);
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
			if(UserJobStatusType.ACCEPTED == status){
				jobDao.acceptJob(loggedInUser.getId(), jobId, status);
				result.put(HollerConstants.SUCCESS, HollerConstants.JOB_ACCEPTED_SUCCESSFULLY);
			}else if(UserJobStatusType.UNACCEPT == status){
				jobDao.unAcceptJob(loggedInUser.getId(), jobId);
				result.put(HollerConstants.SUCCESS, HollerConstants.JOB_UNACCEPTED_SUCCESSFULLY);
			}
			return result;
		}
	}

	@Transactional
	public Map<String, Object> grantOrUnGrantJob(int userId, int jobId, UserJobStatusType status, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(UserJobStatusType.GRANTED == status){
			jobDao.grantOrUnGrantJob(userId, jobId, status);
			result.put(HollerConstants.SUCCESS, HollerConstants.JOB_GRANTED_SUCCESSFULLY);
		}else if(UserJobStatusType.UNGRANT == status){
			jobDao.grantOrUnGrantJob(userId, jobId, UserJobStatusType.ACCEPTED);
			result.put(HollerConstants.SUCCESS, HollerConstants.JOB_UNGRANTED_SUCCESSFULLY);
		}
		return result;
	}
}
