package com.holler.holler_dao;

import com.holler.holler_dao.entity.Notification;
import com.holler.holler_dao.entity.Rating;
import com.holler.holler_dao.entity.User;

import java.util.List;

public interface RatingDao extends BaseDao<Rating> {

	List<Object[]> getUserForRatingScreen(Integer userId);

	List<Object[]> getUserRatings(Integer userId);
}
