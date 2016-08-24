package com.holler.holler_service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import com.holler.holler_dao.util.HollerProperties;

@Service
public class AttachmentsServiceImpl implements AttachmentsService{

	static final Logger log = LogManager.getLogger(AttachmentsServiceImpl.class.getName());
	
	@Autowired
	UserDao userDao;

	@Autowired
	UserDocumentDao userDocumentDao;
	
	@Autowired
	TokenService tokenService;
	
	@Transactional
	public Map<String, Object> saveDocument(MultipartFile uploadedFile, DocumentType uploadedFileType, Integer userId, HttpServletRequest request) {
		log.info("saveDocument :: called");
		Map<String, Object> result = new HashMap<String, Object>();
		 if(tokenService.isValidToken(request)){
		 //if(Boolean.TRUE){
			 log.info("saveDocument :: valid token");
			 log.info("saveDocument :: user {} uploaded doc type {}", userId, uploadedFileType);
			 String fileName = null;
			switch(uploadedFileType){
			case PROFILE_IMAGE:
				fileName = saveDocument(uploadedFile, HollerProperties.getInstance().getValue("holler.file.fileUploadPath") + HollerProperties.getInstance().getValue("holler.file.folderSeparator") + HollerProperties.getInstance().getValue("holler.file.profileImageFolder"));
				User user = userDao.findById(userId);
				user.setPic(uploadedFile.getOriginalFilename());
				userDao.update(user);
				break;
			default:
				fileName = saveDocument(uploadedFile, HollerProperties.getInstance().getValue("holler.file.fileUploadPath") + HollerProperties.getInstance().getValue("holler.file.folderSeparator") + HollerProperties.getInstance().getValue("holler.file.otherDocumentFolder"));
				UserDocument userDocument = new UserDocument(userDao.findById(userId), uploadedFile.getOriginalFilename(), uploadedFileType);
				userDocumentDao.save(userDocument);
				break;
			}
			result.put(HollerConstants.STATUS, HollerConstants.SUCCESS);
			result.put(HollerConstants.RESULT, fileName);
		}else{
			result.put(HollerConstants.STATUS, HollerConstants.FAILURE);
			result.put(HollerConstants.MESSAGE, HollerConstants.TOKEN_VALIDATION_FAILED);
		}
		 return result;
	}

	private String saveDocument(MultipartFile uploadedFile, String folder) {
		File fileToSave = null;
		String uploadedFileName = UUID.randomUUID() + uploadedFile.getOriginalFilename();
		try {
			String path = folder + HollerProperties.getInstance().getValue("holler.file.folderSeparator") + uploadedFileName;
			log.info("saveDocument :: doc will be saved to path {}", path);
			fileToSave = new File(path);
			fileToSave.createNewFile();
			FileOutputStream fos = new FileOutputStream(fileToSave); 
		    fos.write(uploadedFile.getBytes());
		    fos.close(); 
		} catch (IOException e) {
			e.printStackTrace();
		}
		return uploadedFileName;
	}

}
