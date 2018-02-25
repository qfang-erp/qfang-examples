package com.qfang.examples.spring.scope;

import com.qfang.examples.spring.DemoBean;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author: liaozhicheng.cn@163.com
 * @date: 2018-02-05
 * @since: 1.0
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:scope/applicationContext.xml");
        DemoBean db = context.getBean(DemoBean.class);
        System.out.println("main thread : " + db);

        Thread t1 = new Thread(() -> {
            DemoBean db2 = context.getBean(DemoBean.class);
            System.out.println("thread1 : " + db2);

            DemoBean db3 = context.getBean(DemoBean.class);
            System.out.println("thread1 : " + db3);
        });

        Thread t2 = new Thread(() -> {
            DemoBean db3 = context.getBean(DemoBean.class);
            System.out.println("thread2 : " + db3);
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }

}
