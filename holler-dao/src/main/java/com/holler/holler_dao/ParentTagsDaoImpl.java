package com.holler.holler_dao;

import org.springframework.stereotype.Repository;

import com.holler.holler_dao.entity.ParentTags;

@Repository
public class ParentTagsDaoImpl extends BaseDaoImpl<ParentTags> implements ParentTagsDao {

	public ParentTagsDaoImpl() {
		super(ParentTags.class);
	}

	

}
