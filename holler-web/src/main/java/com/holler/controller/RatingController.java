package com.holler.controller;


import com.holler.bean.UserRatingDTO;
import com.holler.holler_service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@Controller
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    RatingService ratingService;

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> getUserRatings(HttpServletRequest request) {
        return ratingService.getUserRatings(request);
    }

    @RequestMapping(value = "/showScreen", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> showRatingScreen(HttpServletRequest request) throws Exception {
        return ratingService.showRatingScreen(request);
    }

    @RequestMapping(value = "/set", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> setRating(@RequestBody UserRatingDTO userRatingDTO, HttpServletRequest request) throws Exception {
        return ratingService.setRating(userRatingDTO, request);
    }
}
