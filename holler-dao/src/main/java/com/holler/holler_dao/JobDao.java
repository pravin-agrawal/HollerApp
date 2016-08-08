package com.holler.holler_dao;

import com.holler.holler_dao.entity.Jobs;
import com.holler.holler_dao.entity.User;
import com.holler.holler_dao.entity.enums.UserJobStatusType;

import java.util.List;
import java.util.Set;

public interface JobDao extends BaseDao<Jobs> {

    public List<Jobs> searchJobsByTag(String tag);

    public List<Jobs> findAllByUserId(final Integer userId);

    public List<Jobs> getMyPingedJobs(final Integer userId);

    public List<Object[]> getUserAcceptedJobs(final Integer jobId);

    public List<Jobs> searchJobsByTagIds(Set<Integer> tagIds);

    void acceptJob(int userId, int jobId, UserJobStatusType accepted);

    void unAcceptJob(int userId, int jobId);

    void grantOrUnGrantJob(int userId, int jobId, UserJobStatusType status);

   // void unGrantJob(int userId, int jobId, UserJobStatusType status);
}
