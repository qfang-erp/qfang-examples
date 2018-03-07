package com.qfang.examples.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * Created by walle on 2017/3/25.
 */
public class TestMain {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:ch9/homework/applicationContext.xml");

        DriverManagerDataSource dataSource = context.getBean(DriverManagerDataSource.class);
        System.out.println(dataSource.getUrl());
    }

}
