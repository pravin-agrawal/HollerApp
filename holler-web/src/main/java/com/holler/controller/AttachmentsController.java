package com.holler.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.holler.holler_dao.entity.enums.DocumentType;
import com.holler.holler_service.AttachmentsService;

@Controller
public class AttachmentsController {
	
	@Autowired
	AttachmentsService attachmentsService;
	
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public @ResponseBody Map<String, Object> uploadFile(@RequestParam("uploadedFile") MultipartFile uploadedFile,
			@RequestParam("uploadedFileType") DocumentType uploadedFileType, @RequestParam("userId") Integer userId,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return attachmentsService.saveDocument(uploadedFile, uploadedFileType, userId, request);
	}

}
