package com.qfang.examples.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by walle on 2017/3/25.
 */
public class TestMain {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        HelloWorld hw = context.getBean(HelloWorld.class);
        System.out.println(hw);
        System.out.println("Test github");

    }

}
