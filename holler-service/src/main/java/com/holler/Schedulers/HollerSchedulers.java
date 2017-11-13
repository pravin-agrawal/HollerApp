package com.holler.Schedulers;

import com.holler.holler_dao.UserDao;
import com.holler.holler_service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by pravina on 05/11/17.
 */
@Slf4j
@Component
@Configuration
@EnableScheduling
public class HollerSchedulers {

    @Autowired
    UserDao userDao;

    @Autowired
    NotificationService notificationService;

    //@Scheduled(cron = "0 * * * * ?")
    public void profileCompletionScheduler(){
            log.info("profileCompletionScheduler :: started");
            List<Integer> userIds = userDao.getUsersWithoutProfileTags();
            for(Integer userId:userIds){
                notificationService.createUpdateProfileNotification(userId);
            }
            //userIds.forEach(userId ->  notificationService.createUpdateProfileNotification(userId) );
            log.info("profileCompletionScheduler :: Completed");
        }
}
