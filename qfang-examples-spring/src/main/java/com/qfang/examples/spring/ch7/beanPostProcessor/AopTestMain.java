package com.qfang.examples.spring.ch7.beanPostProcessor;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by walle on 2017/4/23.
 */
public class AopTestMain {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:ch7/applicationContext.xml");
        UserService userService = context.getBean(UserService.class);
        userService.saveUser();
        System.out.println();

        userService.findUser();
    }

}
