package com.holler.qurtzJob;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * Created by pravina on 23/11/16.
 */
@Component("expireJob")
public class ExpireJob{

    /*@Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Expire job has been fired.");

    }*/
    /*public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

    }*/

    public void HelloWorld(){
        System.out.println("Expire job has been fired.");
    }


}
