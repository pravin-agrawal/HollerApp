package com.holler.holler_service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.holler.holler_dao.entity.enums.DocumentType;
import com.holler.holler_dao.entity.enums.TagType;

public interface AttachmentsService {

	Map<String, Object> saveDocument(MultipartFile uploadedFile, DocumentType uploadedFileType, Integer userId, HttpServletRequest request);
	
	Map<String, Object> uploadTagImage(MultipartFile uploadedFile, TagType tagType, Integer tagId);
}
