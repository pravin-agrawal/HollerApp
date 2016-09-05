package com.holler.holler_dao;

import java.util.List;
import java.util.Set;

import com.holler.holler_dao.entity.Jobs;
import com.holler.holler_dao.entity.enums.UserJobStatusType;

public interface JobDao extends BaseDao<Jobs> {

    public List<Jobs> searchJobsByTag(String tag);

    public List<Jobs> findAllByUserId(final Integer userId);

    public List<Jobs> getMyPingedJobs(final Integer userId);

    public List<Integer> getMyPostedAndPingedJobIds(final Integer userId);

    public List<Object[]> getUserAcceptedJobs(final Integer jobId);

    public List<Jobs> searchJobsByTagIds(Set<Integer> tagIds);

    void acceptJob(int userId, int jobId, UserJobStatusType accepted);

    void unAcceptJob(int userId, int jobId);

    void grantOrUnGrantJob(int userId, int jobId, UserJobStatusType status);

	public List<Object[]> getUserJobStatus(int jobId);

    void setUserJobRatingFlag(int userId, int jobId, String jobDesignation);
}
