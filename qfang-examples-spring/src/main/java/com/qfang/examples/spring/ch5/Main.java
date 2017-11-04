package com.qfang.examples.spring.ch5;

import com.qfang.examples.spring.DemoBean;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by walle on 2017/4/8.
 */
public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("ch5/applicationContext.xml");
        DemoBean db = context.getBean(DemoBean.class);
        System.out.printf("main thread, time1, %s \r\n", db);

        new Thread(() -> {
            DemoBean db1 = context.getBean(DemoBean.class);
            System.out.printf("sub thread-1, %s \r\n" ,db1);
        }).start();

        new Thread(() -> {
            DemoBean db1 = context.getBean(DemoBean.class);
            System.out.printf("sub thread-2, %s \r\n" ,db1);
        }).start();

        System.out.printf("main thread, time2, %s \r\n", context.getBean(DemoBean.class));
    }

}
