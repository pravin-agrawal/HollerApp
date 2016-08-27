package com.holler.holler_dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.holler.holler_dao.entity.UserDocument;
import com.holler.holler_dao.entity.enums.DocumentType;
import com.holler.holler_dao.util.CommonUtil;

@Repository
public class UserDocumentDaoImpl extends BaseDaoImpl<UserDocument> implements UserDocumentDao {

	public UserDocumentDaoImpl() {
		super(UserDocument.class);
	}

	public UserDocument getUserDocument(int userId, DocumentType documentType) {
		List<UserDocument> userDocumentList = entityManager.createQuery("from " + UserDocument.class.getName()
				+ " where user.id = :userId AND documentType = :documentType", UserDocument.class)
				.setParameter("userId", userId)
				.setParameter("documentType", documentType).getResultList();
		if(CommonUtil.notNullAndEmpty(userDocumentList)){
			return userDocumentList.get(0);
		}
		return null;
	}
}
