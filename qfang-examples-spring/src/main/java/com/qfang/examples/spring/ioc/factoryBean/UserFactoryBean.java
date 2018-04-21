package com.qfang.examples.spring.ioc.factoryBean;

import com.qfang.examples.spring.common.User;
import org.springframework.beans.factory.FactoryBean;

/**
 * @author: liaozhicheng.cn@163.com
 * @date: 2018-04-07
 * @since: 1.0
 */
public class UserFactoryBean implements FactoryBean<User> {

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

}
