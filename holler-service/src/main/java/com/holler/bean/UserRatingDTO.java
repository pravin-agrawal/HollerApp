package com.holler.bean;

import com.holler.holler_dao.entity.Jobs;
import com.holler.holler_dao.entity.Rating;
import com.holler.holler_dao.entity.enums.JobStatusType;
import com.holler.holler_dao.util.CommonUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

@Getter@Setter
public class UserRatingDTO {
	static final Logger log = LogManager.getLogger(UserRatingDTO.class.getName());

	private Integer toUserId;
	private Integer fromUserId;
	private String toUserName;
	private String toUserProfilePic;
	private String fromUserName;
	private String fromUserProfilePic;
	private int rating;
	private String feedback;
	private int jobId;
	private String jobTitle;
	private String jobDesignation;
	private Date ratingDate;
	private Integer compensation;

	public static List<UserRatingDTO> constructUserDTOForRatingScreen(List<Object[]> resultList) {
		List<UserRatingDTO> userRatingDTOs = new ArrayList<UserRatingDTO>();
		if(resultList != null && !resultList.isEmpty()){
			for (Object[] object : resultList) {
				if(object != null){
					UserRatingDTO userRatingDTO = new UserRatingDTO();
					userRatingDTO.setToUserId((Integer) object[0]);
					userRatingDTO.setToUserName((String) object[1]);
					userRatingDTO.setToUserProfilePic((String) object[2]);
					userRatingDTO.setJobId((Integer) object[3]);
					userRatingDTO.setJobTitle((String) object[4]);
					userRatingDTO.setJobDesignation((String) object[5]);
					userRatingDTOs.add(userRatingDTO);
				}
			}
		}
		return userRatingDTOs;
	}

	public static List<UserRatingDTO> constructUserRatingsDTO(List<Object[]> resultList) {
		List<UserRatingDTO> userJobDTOs = new ArrayList<UserRatingDTO>();
		if(resultList != null && !resultList.isEmpty()){
			for (Object[] object : resultList) {
				if(object != null){
					UserRatingDTO userRatingDTO = new UserRatingDTO();
					userRatingDTO.setFromUserId((Integer) object[0]);
					userRatingDTO.setFromUserName((String) object[1]);
					userRatingDTO.setFromUserProfilePic((String) object[2]);
					userRatingDTO.setRating((Integer) object[3]);
					userRatingDTO.setFeedback((String) object[4]);
					userRatingDTO.setRatingDate((Date)object[5]);
					userRatingDTO.setJobTitle((String) object[6]);
					userRatingDTO.setCompensation((Integer) object[7]);
					userJobDTOs.add(userRatingDTO);
				}
			}
		}
		return userJobDTOs;
	}


	public static Rating constructRatingDTOToSave(UserRatingDTO userRatingDTO) {
		Rating rating = new Rating();
		rating.setObjectId(userRatingDTO.getJobId());
		rating.setRating(userRatingDTO.getRating());
		rating.setFeedback(userRatingDTO.getFeedback());
		return rating;
	}

	public static Float calculateAverageRatingForUser(List<Object[]> resultList) {
		Float avgRating = 0f;
		int totalRating = 0 ;
		int totalCount = 0;
		if(resultList != null && !resultList.isEmpty()){
			for (Object[] object : resultList) {
				if(object != null){
					totalCount++;
					totalRating = totalRating + (Integer) object[3];
				}
			}
		}
		if(totalCount != 0){
			avgRating = (float)totalRating/totalCount;
		}
		return avgRating;
	}
}
