package com.holler.holler_dao;

import com.holler.holler_dao.entity.AppVersion;

public interface AppVersionDao extends BaseDao<AppVersion> {

    AppVersion getLatestVersion();
}
