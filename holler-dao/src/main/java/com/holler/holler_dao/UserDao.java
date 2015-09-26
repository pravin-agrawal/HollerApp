package com.holler.holler_dao;

import java.util.List;

import com.holler.holler_dao.entity.User;

public interface UserDao extends BaseDao<User> {

	public boolean authenticateUser(String email, String password);
	
	public List<Object[]> getUserJobs(int requestUserId);
	
	public User getByEmailAndPassword(String email, String password);
}
