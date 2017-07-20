package com.qfang.examples.spring.ch8.homework;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by walle on 2017/4/30.
 */
public class FileSearchAopConfigMain {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:ch8/homework/applicationContext-aopconfig.xml");
        FileSearcher searcher = context.getBean("fileSearcher", FileSearcher.class);
        searcher.search(".java");
    }

}
