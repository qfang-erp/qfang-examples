package com.qfang.examples.dubbo.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author: liaozhicheng
 * @Timestamp: 2017/05/23 18:05
 */
public class ProviderMain {

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("provider.xml");
        context.start();

        System.in.read();
    }

}
