package com.holler.holler_dao;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.holler.holler_dao.common.SQLQueryIds;
import com.holler.holler_dao.entity.Jobs;
import com.holler.holler_dao.entity.User;
import com.holler.holler_dao.entity.enums.UserJobStatusType;
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
		return entityManager.createQuery("from " + Jobs.class.getName() + " jobs where jobs.user.id in (:userId) "
				 + " order by jobs.created desc", Jobs.class)
				.setParameter("userId", userId).getResultList();
	}

	public List<Jobs> getMyPingedJobs(Integer userId) {
		String sql = queryDao.getQueryString(SQLQueryIds.GET_MY_PINGED_JOBS);
		Query queryObject = entityManager.createNativeQuery(sql, Jobs.class).setParameter("userId", userId);
		List<Jobs> resultList = queryObject.getResultList();
		return resultList;
	}

	public List<User> getUserAcceptedJobs(Integer jobId) {
		String sql = queryDao.getQueryString(SQLQueryIds.GET_ACCEPTED_USERS_BY_JOB_ID);
		Query queryObject = entityManager.createNativeQuery(sql,User.class)
				.setParameter("jobId", jobId)
				.setParameter("userJobStatus", UserJobStatusType.getAcceptedAndGrantedStatus());
		List<User> resultList = queryObject.getResultList();
		return resultList;
	}

	public List<Jobs> searchJobsByTagIds(Set<Integer> tagIds) {
		String sql = queryDao.getQueryString(SQLQueryIds.GET_JOBS_BY_TAG_IDS);
		String tagIdsString = StringUtils.join(tagIds, ',');
		Query queryObject = entityManager.createNativeQuery(sql, Jobs.class)
				.setParameter("tagIdsString", tagIdsString).setParameter("tagIds", tagIds);
		List<Jobs> resultList = queryObject.getResultList();
		return resultList;
	}

	public void acceptJob(int userId, int jobId, UserJobStatusType status) {
		Query query = entityManager.createNativeQuery(queryDao.getQueryString(SQLQueryIds.ACCEPT_JOB));
		query.setParameter("userId", userId);
		query.setParameter("jobId", jobId);
		query.setParameter("status", status.toString());
		query.executeUpdate();
	}

	public void unAcceptJob(int userId, int jobId) {
		Query query = entityManager.createNativeQuery(queryDao.getQueryString(SQLQueryIds.UNACCEPT_JOB));
		query.setParameter("userId", userId);
		query.setParameter("jobId", jobId);
		query.executeUpdate();
	}

	public void grantOrUnGrantJob(int userId, int jobId, UserJobStatusType status) {
		Query query = entityManager.createNativeQuery(queryDao.getQueryString(SQLQueryIds.UPDATE_JOB));
		query.setParameter("userId", userId);
		query.setParameter("jobId", jobId);
		query.setParameter("status", status.toString());
		query.executeUpdate();
	}

/*
	public void unGrantJob(int userId, int jobId, UserJobStatusType status) {
		Query query = entityManager.createNativeQuery(queryDao.getQueryString(SQLQueryIds.UPDATE_JOB));
		query.setParameter("userId", userId);
		query.setParameter("jobId", jobId);
		query.executeUpdate();
	}
*/

}
