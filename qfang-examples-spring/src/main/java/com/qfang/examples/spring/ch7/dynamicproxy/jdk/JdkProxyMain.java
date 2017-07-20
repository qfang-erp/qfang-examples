package com.qfang.examples.spring.ch7.dynamicproxy.jdk;

/**
 * Created by walle on 2017/4/20.
 */
public class JdkProxyMain {

    public static void main(String[] args) {
        DynamicProxyService proxyService = new DynamicProxyService();
        IService service = (IService) proxyService.bind(new RealService());
        service.sayHello();
    }

}
