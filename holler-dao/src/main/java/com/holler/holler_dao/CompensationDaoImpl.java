package com.holler.holler_dao;

import org.springframework.stereotype.Repository;

import com.holler.holler_dao.entity.Compensation;

@Repository
public class CompensationDaoImpl extends BaseDaoImpl<Compensation> implements CompensationDao {

	public CompensationDaoImpl() {
		super(Compensation.class);
	}

}
