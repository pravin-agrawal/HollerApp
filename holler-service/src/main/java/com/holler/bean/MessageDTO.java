package com.holler.bean;

import com.holler.holler_dao.entity.Jobs;
import com.holler.holler_dao.entity.Message;
import com.holler.holler_dao.entity.Tags;
import com.holler.holler_dao.entity.User;
import com.holler.holler_dao.entity.enums.JobMedium;
import com.holler.holler_dao.entity.enums.JobStatusType;
import com.holler.holler_dao.entity.enums.JobType;
import com.holler.holler_dao.entity.enums.UserJobStatusType;
import com.holler.holler_dao.util.AddressConverter;
import com.holler.holler_dao.util.CommonUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

@Getter @Setter
public class MessageDTO {
	static final Logger log = LogManager.getLogger(MessageDTO.class.getName());

	private int id;
	private Integer fromUserId;
	private Integer toUserId;
	private String userName;
	private String userProfilePic;
	private Date lastConversationDate;
	private boolean read;
	private String message;

	public static List<MessageDTO> constructMessageDTOs(List<Object[]> messages) {
		List<MessageDTO> messageDTOs = new ArrayList<MessageDTO>();
		for(Object[] message: CommonUtil.safe(messages)){
			MessageDTO messageDTO = new MessageDTO();
			messageDTO.setId((Integer) message[0]);
			if(message[1].equals(Boolean.TRUE)){
				messageDTO.setRead(Boolean.TRUE);
			}
			messageDTO.setToUserId((Integer) message[7]);
			messageDTO.setUserName((String) message[8]);
			messageDTO.setUserProfilePic((String) message[9]);
			messageDTO.setMessage((String) message[4]);
			messageDTO.setLastConversationDate((Date) message[5]);
			messageDTOs.add(messageDTO);
		}
		return messageDTOs;
	}

	public static List<MessageDTO> constructMessageDTOForSingleUser(List<Message> messages) {
		List<MessageDTO> messageDTOs = new ArrayList<MessageDTO>();
		for(Message message: CommonUtil.safe(messages)){
			MessageDTO messageDTO = new MessageDTO();
			messageDTO.setFromUserId(message.getFromUser());
			messageDTO.setToUserId(message.getToUser());
			messageDTO.setMessage(message.getMessage());
			messageDTO.setLastConversationDate(message.getCreated());
			messageDTOs.add(messageDTO);
		}
		return messageDTOs;
	}

	public static Message constructMessageToSave(MessageDTO messageDTO) {
		Message message = new Message();
		message.setFromUser(messageDTO.getFromUserId());
		message.setToUser(messageDTO.getToUserId());
		message.setMessage(messageDTO.getMessage());
		message.setCreated(new Date());
		message.setLastModified(new Date());
		message.setRead(Boolean.FALSE);
		return message;
	}
}
