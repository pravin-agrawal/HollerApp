package com.holler.holler_dao;

import java.util.List;

import com.holler.holler_dao.entity.Jobs;

public interface JobDao extends BaseDao<Jobs> {
	
	public List<Jobs> searchJobsByTag(String tag);
}
