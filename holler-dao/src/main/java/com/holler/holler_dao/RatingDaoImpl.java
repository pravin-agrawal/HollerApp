package com.holler.holler_dao;

import com.holler.holler_dao.common.SQLQueryIds;
import com.holler.holler_dao.entity.Rating;
import com.holler.holler_dao.entity.enums.JobStatusType;
import com.holler.holler_dao.entity.enums.UserJobStatusType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class RatingDaoImpl extends BaseDaoImpl<Rating> implements RatingDao {

    public RatingDaoImpl() {
        super(Rating.class);
    }

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    QueryDao queryDao;

    @PersistenceContext
    EntityManager entityManager;


    public List<Object[]> getUserForRatingScreen(Integer userId) {
        String sql = queryDao.getQueryString(SQLQueryIds.GET_USER_FOR_RATING_SCREEN);
        Query queryObject = entityManager.createNativeQuery(sql)
                .setParameter("userId", userId)
                .setParameter("jobStatus", JobStatusType.Completed.name())
                .setParameter("userJobStatus", UserJobStatusType.GRANTED.name())
                .setParameter("ratingFlag",Boolean.FALSE);
        List<Object[]> resultList = queryObject.getResultList();
        return resultList;
    }

    public List<Object[]> getUserRatings(Integer userId) {
        String sql = queryDao.getQueryString(SQLQueryIds.GET_USER_RATING_DATA);
        Query queryObject = entityManager.createNativeQuery(sql)
                .setParameter("userId", userId);
        List<Object[]> resultList = queryObject.getResultList();
        return resultList;
    }
}
