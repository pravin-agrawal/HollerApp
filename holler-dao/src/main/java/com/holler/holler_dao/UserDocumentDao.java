package com.holler.holler_dao;

import com.holler.holler_dao.entity.UserDocument;
import com.holler.holler_dao.entity.enums.DocumentType;

public interface UserDocumentDao extends BaseDao<UserDocument>{

	UserDocument getUserDocument(int userId, DocumentType documentType);
}
