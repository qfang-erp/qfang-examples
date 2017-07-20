package com.qfang.examples.spring.ch8.annotationconfig;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by walle on 2017/4/30.
 */
public class AnnotationConfigMain {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "classpath:ch8/annotationconfig/applicationContext.xml");
        UserService userService = context.getBean(UserService.class);
        userService.findUser();

        System.out.println();
        userService.saveUser();

        System.out.println();
        userService.updateUser();
    }

}
