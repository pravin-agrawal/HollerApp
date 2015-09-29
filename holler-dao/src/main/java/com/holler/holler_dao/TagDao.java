package com.holler.holler_dao;

import java.util.List;
import java.util.Set;

import com.holler.holler_dao.entity.Tags;

public interface TagDao extends BaseDao<Tags> {
	
	public List<Tags> findbyIds(Set<Integer> tagIds);
	
}
