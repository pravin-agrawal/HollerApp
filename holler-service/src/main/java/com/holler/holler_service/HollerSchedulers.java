package com.holler.holler_service;

import com.holler.holler_dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

/**
 * Created by pravina on 06/08/17.
 */
public class HollerSchedulers {


    @Autowired
    UserDao userDao;

    @Autowired
    NotificationService notificationService;

    @Scheduled(cron = "0 15 10 15 * ?")
    public void profileCompletionScheduler(){
        List<Integer> userIds = userDao.getUsersWithoutProfileTags();
        userIds.forEach(userId -> {
            notificationService.
        });
    }
}
