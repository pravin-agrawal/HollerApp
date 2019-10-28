package com.holler.holler_dao;

import com.holler.holler_dao.entity.AppVersion;
import org.springframework.stereotype.Repository;

/**
 * Created by pravina on 12/11/17.
 */
@Repository
public class AppVersionDaoImpl extends BaseDaoImpl<AppVersion> implements AppVersionDao {

    public AppVersionDaoImpl() {
        super(AppVersion.class);
    }

    public AppVersion getLatestVersion() {
        return entityManager.createQuery("from " + AppVersion.class.getName() + " app  "
                + " order by app.created desc", AppVersion.class).getResultList().get(0);

    }
}
