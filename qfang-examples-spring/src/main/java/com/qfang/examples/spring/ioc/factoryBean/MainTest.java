package com.qfang.examples.spring.ioc.factoryBean;

import com.qfang.examples.spring.common.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author: liaozhicheng.cn@163.com
 * @date: 2018-04-04
 * @since: 1.0
 */
public class MainTest {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("ioc/factoryBean/applicationContext.xml");
        User user = context.getBean("user", User.class);
        System.out.println(user.toString());
    }

}
