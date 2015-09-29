package com.holler.holler_dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.holler.holler_dao.common.SQLQueryIds;
import com.holler.holler_dao.entity.Jobs;
import com.holler.holler_dao.entity.User;
import com.holler.holler_dao.mapper.UserMapper;

@Repository
public class JobDaoImpl extends BaseDaoImpl<Jobs> implements JobDao {
	
	public JobDaoImpl() {
		super(Jobs.class);
	}
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	QueryDao queryDao;
	
	@PersistenceContext
	EntityManager entityManager;
	
	public boolean authenticateUser(String email, String password){
		String sql = queryDao.getQueryString(SQLQueryIds.AUTHENTICATE_USER);
		List<User> userList = jdbcTemplate.query(sql, new String[]{email, password}, new UserMapper());
		return (null != userList && userList.size() == 1);
	}
	
	public User getByEmailAndPassword(String email, String password){
		String sql = queryDao.getQueryString(SQLQueryIds.AUTHENTICATE_USER);
		List<User> userList = jdbcTemplate.query(sql, new String[]{email, password}, new UserMapper());
		if(userList != null && !userList.isEmpty() && userList.size() == 1){
			return userList.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getUserJobs(int requestUserId){
		String sql = queryDao.getQueryString(SQLQueryIds.GET_USER_JOBS);
		Query queryObject = entityManager.createNativeQuery(sql)
				.setParameter("requestUserId", requestUserId);
		List<Object[]> resultList = queryObject.getResultList();
		return resultList;
	}

	@SuppressWarnings("unchecked")
	public List<Jobs> searchJobsByTag(String tag) {
		String sql = queryDao.getQueryString(SQLQueryIds.GET_JOBS_BY_TAG);
		Query queryObject = entityManager.createNativeQuery(sql, Jobs.class).setParameter("searchedTag", tag + "%");
		List<Jobs> resultList = queryObject.getResultList();
		return resultList;
	}
}
