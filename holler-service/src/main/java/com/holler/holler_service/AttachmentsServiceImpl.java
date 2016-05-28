package com.holler.holler_service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.holler.holler_dao.UserDao;
import com.holler.holler_dao.UserDocumentDao;
import com.holler.holler_dao.common.HollerConstants;
import com.holler.holler_dao.entity.User;
import com.holler.holler_dao.entity.UserDocument;
import com.holler.holler_dao.entity.enums.DocumentType;

@Service
public class AttachmentsServiceImpl implements AttachmentsService{

	@Autowired
	UserDao userDao;

	@Autowired
	UserDocumentDao userDocumentDao;
	
	@Autowired
	TokenService tokenService;
	
	private String folderSeparator = "\\";
	private String fileUploadPath = "D:\\HollerFileUpload";
	private String profileImageFolder = "images\\profile";
	private String otherDocumentFolder = "documents";
	
	@Transactional
	public Map<String, Object> saveDocument(MultipartFile uploadedFile, DocumentType uploadedFileType, Integer userId, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		 if(tokenService.isValidToken(request)){
		 //if(Boolean.TRUE){
			String docUrl = "";
			switch(uploadedFileType){
			case PROFILE_IMAGE:
				docUrl = saveDocument(uploadedFile, fileUploadPath + folderSeparator + profileImageFolder);
				User user = userDao.findById(userId);
				user.setPic(docUrl);
				userDao.update(user);
				break;
			default:
				docUrl = saveDocument(uploadedFile, fileUploadPath + folderSeparator + otherDocumentFolder);
				UserDocument userDocument = new UserDocument(userDao.findById(userId), docUrl, uploadedFileType);
				userDocumentDao.save(userDocument);
				break;
			}
			result.put(HollerConstants.STATUS, HollerConstants.SUCCESS);
			result.put(HollerConstants.RESULT, docUrl);
		}else{
			result.put(HollerConstants.STATUS, HollerConstants.FAILURE);
			result.put(HollerConstants.MESSAGE, HollerConstants.TOKEN_VALIDATION_FAILED);
		}
		 return result;
	}

	private String saveDocument(MultipartFile uploadedFile, String folder) {
		File fileToSave = null;
		try {
			fileToSave = new File(folder + folderSeparator + UUID.randomUUID() + uploadedFile.getOriginalFilename());
			fileToSave.createNewFile();
			FileOutputStream fos = new FileOutputStream(fileToSave); 
		    fos.write(uploadedFile.getBytes());
		    fos.close(); 
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileToSave.getAbsolutePath();
	}

}
