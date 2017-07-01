package com.holler;

import com.holler.qurtzJob.QuartzConfig;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;

/**
 * Created by pravina on 28/03/17.
 */
@Configuration
@ComponentScan
@EnableTransactionManagement
@EnableScheduling
public class Application {

    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        //applicationContext.getEnvironment().getPropertySources().addFirst(new ResourcePropertySource("classpath:k2-etl.properties"));
        applicationContext.register(QuartzConfig.class);
        applicationContext.refresh();
    }
}

