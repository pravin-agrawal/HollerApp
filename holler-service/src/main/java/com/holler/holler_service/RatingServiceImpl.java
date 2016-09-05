package com.holler.holler_service;

import com.holler.bean.UserRatingDTO;
import com.holler.holler_dao.JobDao;
import com.holler.holler_dao.RatingDao;
import com.holler.holler_dao.UserDao;
import com.holler.holler_dao.common.HollerConstants;
import com.holler.holler_dao.entity.Rating;
import com.holler.holler_dao.entity.enums.UserJobStatusType;
import com.holler.holler_dao.util.CommonUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RatingServiceImpl implements RatingService {

    static final Logger log = LogManager.getLogger(RatingServiceImpl.class.getName());

    @Autowired
    RatingDao ratingDao;

    @Autowired
    UserDao userDao;

    @Autowired
    JobDao jobDao;

    @Autowired
    TokenService tokenService;

    @Transactional
    public Map<String, Object> showRatingScreen(HttpServletRequest request) {
        log.info("showRatingScreen :: called");
        Map<String, Object> result = new HashMap<String, Object>();
        //if (tokenService.isValidToken(request)) {
            if(Boolean.TRUE){
            log.info("showRatingScreen :: valid token");
            log.info("showRatingScreen :: show rating screen for user with id {}", request.getHeader("userId"));
            List<Object[]> resultList = ratingDao.getUserForRatingScreen(Integer.valueOf(request.getHeader("userId")));
            List<UserRatingDTO> userRatingDTOs = UserRatingDTO.constructUserDTOForRatingScreen(resultList);
            if (CommonUtil.isNotNull(userRatingDTOs)) {
                result.put(HollerConstants.STATUS, HollerConstants.SUCCESS);
                result.put(HollerConstants.RESULT, userRatingDTOs);
            }
        } else {
            result.put(HollerConstants.STATUS, HollerConstants.FAILURE);
            result.put(HollerConstants.MESSAGE, HollerConstants.TOKEN_VALIDATION_FAILED);
        }
        return result;
    }

    @Transactional
    public Map<String, Object> getUserRatings(HttpServletRequest request) {
        log.info("getUserRatings :: called");
        Map<String, Object> result = new HashMap<String, Object>();
        //if (tokenService.isValidToken(request)) {
            if(Boolean.TRUE){
            log.info("getUserRatings :: valid token");
            log.info("getUserRatings :: get user rating for user with id {}", request.getHeader("userId"));
            List<Object[]> resultList = ratingDao.getUserRatings(Integer.valueOf(request.getHeader("userId")));
            List<UserRatingDTO> userRatingDTOs = UserRatingDTO.constructUserRatingsDTO(resultList);
            if (CommonUtil.isNotNull(userRatingDTOs)) {
                result.put(HollerConstants.STATUS, HollerConstants.SUCCESS);
                result.put(HollerConstants.RESULT, userRatingDTOs);
            }
        } else {
            result.put(HollerConstants.STATUS, HollerConstants.FAILURE);
            result.put(HollerConstants.MESSAGE, HollerConstants.TOKEN_VALIDATION_FAILED);
        }
        return result;
    }

    @Transactional
    public Map<String, Object> setRating(UserRatingDTO userRatingDTO, HttpServletRequest request) {
        log.info("setRating :: called");
        Map<String, Object> result = new HashMap<String, Object>();
        //if (tokenService.isValidToken(request)) {
            if(Boolean.TRUE){
            log.info("setRating :: valid token");
            log.info("setRating :: set rating for userId {}", userRatingDTO.getToUserId());
            Rating rating = UserRatingDTO.constructRatingDTOToSave(userRatingDTO);
            rating.setToUser(userDao.findById(userRatingDTO.getToUserId()));
            rating.setFromUser(userDao.findById(userRatingDTO.getFromUserId()));
            ratingDao.save(rating);
                if(userRatingDTO.getJobDesignation().equals(UserJobStatusType.ACCEPTER.name())){
                    jobDao.setUserJobRatingFlag(userRatingDTO.getFromUserId(),userRatingDTO.getJobId(),userRatingDTO.getJobDesignation());
                }else{
                    jobDao.setUserJobRatingFlag(userRatingDTO.getToUserId(),userRatingDTO.getJobId(),userRatingDTO.getJobDesignation());
                }
            result.put(HollerConstants.STATUS, HollerConstants.SUCCESS);
            result.put(HollerConstants.RESULT, userRatingDTO);
        } else {
            result.put(HollerConstants.STATUS, HollerConstants.FAILURE);
            result.put(HollerConstants.MESSAGE, HollerConstants.TOKEN_VALIDATION_FAILED);
        }
        return result;
    }
}
