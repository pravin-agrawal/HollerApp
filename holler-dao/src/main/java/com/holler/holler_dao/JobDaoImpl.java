package com.holler.holler_dao;

import com.holler.holler_dao.common.SQLQueryIds;
import com.holler.holler_dao.entity.Jobs;
import com.holler.holler_dao.entity.User;
import com.holler.holler_dao.entity.enums.UserJobStatusType;
import com.holler.holler_dao.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

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

	public boolean authenticateUser(String email, String password) {
		String sql = queryDao.getQueryString(SQLQueryIds.AUTHENTICATE_USER);
		List<User> userList = jdbcTemplate.query(sql, new String[]{email, password}, new UserMapper());
		return (null != userList && userList.size() == 1);
	}

	public User getByEmailAndPassword(String email, String password) {
		String sql = queryDao.getQueryString(SQLQueryIds.AUTHENTICATE_USER);
		List<User> userList = jdbcTemplate.query(sql, new String[]{email, password}, new UserMapper());
		if (userList != null && !userList.isEmpty() && userList.size() == 1) {
			return userList.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getUserJobs(int requestUserId) {
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

	@Transactional(readOnly = true)
	public List<Jobs> findAllByUserId(final Integer userId) {
		return entityManager.createQuery("from " + Jobs.class.getName() + " jobs where jobs.user.id in (:userId)", Jobs.class)
				.setParameter("userId", userId).getResultList();
	}

	public List<User> getUserAcceptedJobs(Integer jobId) {
		String sql = queryDao.getQueryString(SQLQueryIds.GET_ACCEPTED_USERS_BY_JOB_ID);
        /*TypedQuery<User> query = entityManager.createQuery(sql,User.class);
        query.setParameter("jobId", jobId);
        query .setParameter("userJobStatus", UserJobStatusType.Accepted.toString());
        return query.getResultList();*/

		Query queryObject = entityManager.createNativeQuery(sql,User.class)
				.setParameter("jobId", jobId)
				.setParameter("userJobStatus", UserJobStatusType.Accepted.toString());
		List<User> resultList = queryObject.getResultList();
		return resultList;
	}
}
