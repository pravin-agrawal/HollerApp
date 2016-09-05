package com.holler.holler_service;

import com.holler.bean.NotificationDTO;
import com.holler.bean.UserRatingDTO;
import com.holler.holler_dao.entity.enums.NotificationType;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;


public interface RatingService {

	Map<String,Object> showRatingScreen(HttpServletRequest request);

	Map<String,Object> getUserRatings(HttpServletRequest request);

	Map<String,Object> setRating(UserRatingDTO userRatingDTO, HttpServletRequest request);
}
