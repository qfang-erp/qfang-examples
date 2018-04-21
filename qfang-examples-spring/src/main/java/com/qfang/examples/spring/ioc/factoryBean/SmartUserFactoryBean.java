package com.qfang.examples.spring.ioc.factoryBean;

import com.qfang.examples.spring.common.User;
import org.springframework.beans.factory.SmartFactoryBean;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.FactoryBeanRegistrySupport;

/**
 * {@link #isEagerInit} 返回 true，在这个 factoryBean 实例被创建之后，会立即创建 User bean 实例，{@link DefaultListableBeanFactory#preInstantiateSingletons}
 * 并且进行缓存 {@link AbstractBeanFactory#getObjectForBeanInstance} & {@link FactoryBeanRegistrySupport#getObjectFromFactoryBean}
 *
 *
 * @author: liaozhicheng.cn@163.com
 * @date: 2018-04-04
 * @since: 1.0
 */
public class SmartUserFactoryBean implements SmartFactoryBean<User> {

    @Override
    public User getObject() throws Exception {
        return new User("SmartUserFactoryBean", "123456");
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
