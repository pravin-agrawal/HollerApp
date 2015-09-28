package com.holler.holler_dao;

import com.holler.holler_dao.entity.Jobs;

public interface JobDao extends BaseDao<Jobs> {
	
	public Jobs postJob(Jobs job);
}
