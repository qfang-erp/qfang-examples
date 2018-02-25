package com.qfang.examples.spring.d01;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author: liaozhicheng.cn@163.com
 * @date: 2017-12-01
 * @since: 1.0
 */
public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:d01/applicationContext.xml");
        DemoBean bean = context.getBean(DemoBean.class);
        System.out.println(bean.getName());
    }

}
