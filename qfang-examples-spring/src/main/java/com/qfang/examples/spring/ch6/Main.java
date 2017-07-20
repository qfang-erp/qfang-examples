package com.qfang.examples.spring.ch6;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by walle on 2017/4/16.
 */
public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("ch6/applicationContext.xml");
        User user = context.getBean(User.class);
        System.out.println(user);
    }

}
