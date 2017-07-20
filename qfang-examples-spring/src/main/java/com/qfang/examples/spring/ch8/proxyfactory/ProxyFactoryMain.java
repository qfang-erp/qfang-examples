package com.qfang.examples.spring.ch8.proxyfactory;

import com.qfang.examples.spring.ch8.*;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

/**
 * Created by walle on 2017/4/29.
 */
public class ProxyFactoryMain {

    public static void main(String[] args) {
//        addAdvice();

        getProxyClass();
    }

    private static void addAdvice() {
        IUserService userService = new UserServiceImpl();

        // addAdvice & addAdvisor
        // addAdvice 方法接收一个 Advice 对象，但内部实现还是封装成 Advisor
        ProxyFactory proxyFactory = new ProxyFactory(userService);
        proxyFactory.addAdvice(new SecurityInterceptor());
        proxyFactory.addAdvisor(new DefaultPointcutAdvisor(new LogBeforeAdvice()));

        IUserService proxyUserService = (IUserService) proxyFactory.getProxy();
        proxyUserService.findUser();
    }

    private static void getProxyClass() {
        // 代理的原始对象如果有实现接口，spring 将使用 jdk 动态代理方式创建代理类
        // 如果代理原始对象没有实现任何接口，spring 将使用 CGLib 方式创建动态代理类
        IUserService userService = new UserServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(userService);
        proxyFactory.addAdvice(new SecurityInterceptor());
        IUserService proxyUserService = (IUserService) proxyFactory.getProxy();
        System.out.println(proxyUserService.getClass());

        UserService serviceSupport = new UserService();
        ProxyFactory proxyFactory1 = new ProxyFactory(serviceSupport);
        proxyFactory1.addAdvice(new SecurityInterceptor());
        UserService proxyService = (UserService) proxyFactory1.getProxy();
        System.out.println(proxyService.getClass());
    }

}
