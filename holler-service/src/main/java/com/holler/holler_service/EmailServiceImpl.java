package com.holler.holler_service;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.holler.bean.EmailDTO;
import com.holler.holler_dao.UserDao;
import com.holler.holler_dao.common.HollerConstants;
import com.holler.holler_dao.entity.User;
import com.holler.holler_dao.util.HollerProperties;

@Service
public class EmailServiceImpl implements EmailService{

	static final Logger log = LogManager.getLogger(EmailServiceImpl.class.getName());
	
	@Autowired
	TokenService tokenService;

	@Autowired
	UserDao userDao;
	
	public Map<String, Object> sendEmail(EmailDTO emailDTO, HttpServletRequest request){
		log.info("sendEmail :: called");
		Map<String, Object> result = new HashMap<String, Object>();
		if(tokenService.isValidToken(request)){
		//if(Boolean.TRUE){
			 log.info("sendEmail :: valid token");
			 String content = "Email sent by user id : " + emailDTO.getUserId() + "<br/>" + emailDTO.getContent();;
			 sendMail(HollerProperties.getInstance().getValue("mail.smtp.user"), emailDTO.getSubject(), content);
			 log.info("sendEmail :: mail sent to {}", HollerProperties.getInstance().getValue("mail.smtp.user"));
/*
			 User user = userDao.findById(emailDTO.getUserId()); 
			 sendMail(user.getEmail(), HollerProperties.getInstance().getValue("support.mail.ack.subject"), HollerProperties.getInstance().getValue("support.mail.ack.body"));
			 log.info("sendEmail :: mail sent to {}", user.getEmail());
			
*/			 result.put(HollerConstants.STATUS, HollerConstants.SUCCESS);
			result.put(HollerConstants.RESULT, "Mail sent successfully!");
		}else{
			result.put(HollerConstants.STATUS, HollerConstants.FAILURE);
			result.put(HollerConstants.MESSAGE, HollerConstants.TOKEN_VALIDATION_FAILED);
		}
		return result;
	}
	
	private void sendMail(String toEmailAddress, String subject, String messageBody){
		Properties props = System.getProperties();
	    props.put("mail.smtp.starttls.enable", true); // added this line
	    props.put("mail.smtp.host", HollerProperties.getInstance().getValue("mail.smtp.host"));
	    props.put("mail.smtp.user", HollerProperties.getInstance().getValue("mail.smtp.user"));
	    props.put("mail.smtp.password", HollerProperties.getInstance().getValue("mail.smtp.password"));
	    props.put("mail.smtp.port", HollerProperties.getInstance().getValue("mail.smtp.port"));
	    props.put("mail.smtp.auth", true);
	    
	    Session session = Session.getInstance(props,null);
	    MimeMessage message = new MimeMessage(session);

	    // Create the email addresses involved
	    try {
	        InternetAddress from = new InternetAddress(HollerProperties.getInstance().getValue("mail.smtp.user"));
	        message.setSubject(subject);
	        message.setFrom(from);
	        message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmailAddress));

	        // Create a multi-part to combine the parts
	        Multipart multipart = new MimeMultipart("alternative");

	        // Create your text message part
	        BodyPart messageBodyPart = new MimeBodyPart();
	        messageBodyPart.setText("some text to send");

	        // Add the text part to the multipart
	        multipart.addBodyPart(messageBodyPart);

	        // Create the html part
	        messageBodyPart = new MimeBodyPart();
	        String htmlMessage = messageBody;
	        messageBodyPart.setContent(htmlMessage, "text/html");


	        // Add html part to multi part
	        multipart.addBodyPart(messageBodyPart);

	        // Associate multi-part with message
	        message.setContent(multipart);

	        // Send message
	        Transport transport = session.getTransport("smtp");
	        transport.connect(HollerProperties.getInstance().getValue("mail.smtp.host"), 
	        		HollerProperties.getInstance().getValue("mail.smtp.user"), 
	        		HollerProperties.getInstance().getValue("mail.smtp.password"));
	        transport.sendMessage(message, message.getAllRecipients());


	    } catch (AddressException e) {
	        log.error("Error in sending email", e);
	    } catch (MessagingException e) {
	        log.error("Error in sending email", e);
	    }
	}
}
