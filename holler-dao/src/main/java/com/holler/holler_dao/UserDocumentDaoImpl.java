package com.holler.holler_dao;

import org.springframework.stereotype.Repository;

import com.holler.holler_dao.entity.UserDocument;

@Repository
public class UserDocumentDaoImpl extends BaseDaoImpl<UserDocument> implements UserDocumentDao {

	public UserDocumentDaoImpl() {
		super(UserDocument.class);
	}

}
