package com.qfang.examples.spring.ch5;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by walle on 2017/4/8.
 */
public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("ch5/applicationContext.xml");
        ExampleBean eb = context.getBean(ExampleBean.class);
        System.out.printf("main thread, time1, %s \r\n", eb);

        new Thread(() -> {
            ExampleBean eb1 = context.getBean(ExampleBean.class);
            System.out.printf("sub thread-1, %s \r\n" ,eb1);
        }).start();

        new Thread(() -> {
            ExampleBean eb1 = context.getBean(ExampleBean.class);
            System.out.printf("sub thread-2, %s \r\n" ,eb1);
        }).start();

        System.out.printf("main thread, time2, %s \r\n", context.getBean(ExampleBean.class));
    }

}
