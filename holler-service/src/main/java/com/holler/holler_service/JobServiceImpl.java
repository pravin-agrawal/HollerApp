package com.holler.holler_service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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


	public List<UserJobDTO> searchJobsByTag(String tag, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session == null){
			return null;
		}else{
			List<Jobs> jobs = jobDao.searchJobsByTag(tag);
			List<UserJobDTO> jobDTOs = UserJobDTO.getJobDtosFronJobs(jobs);
			return jobDTOs;
		}
	}
	
}
