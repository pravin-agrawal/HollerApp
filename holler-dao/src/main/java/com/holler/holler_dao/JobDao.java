package com.holler.holler_dao;

import java.util.List;

import com.holler.holler_dao.entity.Jobs;
import com.holler.holler_dao.entity.User;

public interface JobDao extends BaseDao<Jobs> {

	public List<Jobs> searchJobsByTag(String tag);
	public List<Jobs> findAllByUserId(final Integer userId);
	public List<User> getUserAcceptedJobs(final Integer jobId);

}
