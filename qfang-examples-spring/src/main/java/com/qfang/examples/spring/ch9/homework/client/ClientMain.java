package com.qfang.examples.spring.ch9.homework.client;

import com.qfang.examples.spring.ch9.homework.service.AccountService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by walle on 2017/5/7.
 */
public class ClientMain {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:ch9/homework/applicationContext.xml");
        AccountService accountService = context.getBean(AccountService.class);

        accountService.transferAmount("zhangsan", "lisi", 200);
    }

}
