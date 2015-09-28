package com.holler.holler_service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.holler.bean.UserJobDTO;
import com.holler.holler_dao.JobDao;
import com.holler.holler_dao.TagDao;
import com.holler.holler_dao.UserDao;
import com.holler.holler_dao.entity.Jobs;
import com.holler.holler_dao.entity.Tags;

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
		String[] strArray = userJobDTO.getTags().split(",");
		Set<Integer> tagSet = new HashSet<Integer>(strArray.length);
		for(int i = 0; i < strArray.length; i++) {
			tagSet.add(Integer.parseInt(strArray[i]));
		}
		Set<Tags> tags = new HashSet<Tags>(tagDao.findbyIds(tagSet));
		job.setTags(tags);
		jobDao.save(job);
		userJobDTO.setJobId(job.getId());
		return userJobDTO;
	}
	
}
