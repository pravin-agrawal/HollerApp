package com.holler.holler_service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.holler.holler_dao.entity.enums.DocumentType;

public interface AttachmentsService {

	Map<String, Object> saveDocument(MultipartFile uploadedFile, DocumentType uploadedFileType, Integer userId, HttpServletRequest request);
}
