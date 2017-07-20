package com.qfang.examples.spring.ch8.proxyfactorybean;

import com.qfang.examples.spring.ch8.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by walle on 2017/4/29.
 */
public class ProxyFactoryBeanMain {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:ch8/proxyfactorybean/applicationContext.xml");
        UserService userService = context.getBean("proxyFactoryBean", UserService.class);
        userService.findUser();
        System.out.println(userService.getClass());
    }

}
