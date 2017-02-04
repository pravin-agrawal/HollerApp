package com.holler.holler_dao;

import java.util.List;
import java.util.Set;

import com.holler.holler_dao.entity.User;

public interface UserDao extends BaseDao<User> {

	public boolean authenticateUser(String email, String password);
	
	public boolean authenticateUserWithEmail(String email);

	public List<Object[]> getUserJobs(int requestUserId);
	
	public User getByEmailAndPassword(String email, String password);

	boolean checkIfUserExists(String email, String phoneNumber);

	public User getByPhoneNumber(String phoneNumber);

	public User findByIdWithTags(int userId);
	
	public Set<Integer> getUserIdsByTagIds(Set<Integer> tagIds);
	
	boolean checkIfUserExists(String email);
	
	public User getByEmail(String email);

	Set<Integer> getAcceptedUserListByJobId(int objectId);

	User findByEmail(String email);
}
