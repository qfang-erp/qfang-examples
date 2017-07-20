package com.qfang.examples.dubbo.client;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author: liaozhicheng
 * @Timestamp: 2017/05/23 18:05
 */
public class ConsumerMain {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("consumer.xml");

        MyService myService = context.getBean("myService", MyService.class);
        System.out.println(myService.sayHello("zhangsan"));
    }

}
