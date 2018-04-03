package com.qfang.examples.spring.ioc.factoryBean;

import com.qfang.examples.spring.common.User;
import org.springframework.beans.factory.SmartFactoryBean;

/**
 * @author: liaozhicheng.cn@163.com
 * @date: 2018-04-04
 * @since: 1.0
 */
public class UserFactoryBean implements SmartFactoryBean<User> {

    @Override
    public User getObject() throws Exception {
        return new User("UserFactoryBean", "123456");
    }

    @Override
    public Class<?> getObjectType() {
        return User.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public boolean isPrototype() {
        return false;
    }

    @Override
    public boolean isEagerInit() {
        return true;
    }
}
