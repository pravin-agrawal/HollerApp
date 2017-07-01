package com.holler.qurtzJob;

import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * Created by pravina on 23/11/16.
 */
@Configuration
public class QuartzConfig {

    private static final Long START_DELAY = 3000L;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private Environment env;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private PlatformTransactionManager transactionManager;
    @Autowired
    private ExpireJob expireJob;

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
        Trigger[] triggers = {
                expireJobCronTriggerFactoryBean().getObject()
        };
        scheduler.setTriggers(triggers);

//        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
//        jobFactory.setApplicationContext(applicationContext);
//        scheduler.setApplicationContext(applicationContext);
//        scheduler.setJobFactory(jobFactory);
//        scheduler.setTransactionManager(transactionManager);
//        scheduler.setSchedulerName("flag-scheduler");
//        scheduler.setDataSource(dataSource);
        return scheduler;
    }

//
//    @Bean
//    public CronTriggerFactoryBean expireJobCronTriggerFactoryBean() {
//        CronTriggerFactoryBean stFactory = new CronTriggerFactoryBean();
//        stFactory.setJobDetail(jobDetails().getObject());
//        stFactory.setStartDelay(START_DELAY);
//        stFactory.setName("expireJobCronTriggerFactoryBean");
//        stFactory.setGroup("expireJob");
//        stFactory.setCronExpression(env.getProperty("job.noContact.cron"));
//        return stFactory;
//    }

    @Bean
    public SimpleTriggerFactoryBean expireJobCronTriggerFactoryBean() {
        SimpleTriggerFactoryBean stFactory = new SimpleTriggerFactoryBean();
        stFactory.setJobDetail(jobDetails().getObject());
        stFactory.setStartDelay(1000);
        stFactory.setRepeatInterval(2000);
        return stFactory;
    }

    @Bean
    public MethodInvokingJobDetailFactoryBean jobDetails() {
        MethodInvokingJobDetailFactoryBean methodInvokingJobDetailFactoryBean = new MethodInvokingJobDetailFactoryBean();
        methodInvokingJobDetailFactoryBean.setTargetBeanName("expireJob");
        methodInvokingJobDetailFactoryBean.setTargetMethod("HelloWorld");
        return methodInvokingJobDetailFactoryBean;

    }


    /*@Bean
    public JobDetailFactoryBean expireJobFactoryBean() {
        JobDetailFactoryBean factory = new JobDetailFactoryBean();
        factory.setJobClass(ExpireJob.class);
        factory.setGroup("expireJob");
        factory.setName("expireJobFactoryBean");
        return factory;
    }*/


}
