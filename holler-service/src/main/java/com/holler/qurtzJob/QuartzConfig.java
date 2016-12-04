package com.holler.qurtzJob;

import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * Created by pravina on 23/11/16.
 */
public class QuartzConfig {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private Environment env;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private PlatformTransactionManager transactionManager;

    private static final Long START_DELAY = 3000L;

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
        Trigger[] triggers = {
                expireJobCronTriggerFactoryBean().getObject()
        };
        scheduler.setTriggers(triggers);

        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        scheduler.setApplicationContext(applicationContext);
        scheduler.setJobFactory(jobFactory);
        scheduler.setTransactionManager(transactionManager);
        scheduler.setSchedulerName("flag-scheduler");
        scheduler.setDataSource(dataSource);
        return scheduler;
    }


    @Bean
    public CronTriggerFactoryBean expireJobCronTriggerFactoryBean() {
        CronTriggerFactoryBean stFactory = new CronTriggerFactoryBean();
        stFactory.setJobDetail(expireJobFactoryBean().getObject());
        stFactory.setStartDelay(START_DELAY);
        stFactory.setName("noLoginCronTriggerFactoryBean");
        stFactory.setGroup("noLogin");
        stFactory.setCronExpression(env.getProperty("job.noContact.cron"));
        return stFactory;
    }

    @Bean
    public JobDetailFactoryBean expireJobFactoryBean() {
        JobDetailFactoryBean factory = new JobDetailFactoryBean();
        factory.setJobClass(ExpireJob.class);
        factory.setGroup("expireJob");
        factory.setName("expireJobFactoryBean");
        return factory;
    }
}
