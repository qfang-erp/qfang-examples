package com.qfang.examples.spring.ch7.dynamicproxy.cglib;

/**
 * Created by walle on 2017/4/28.
 */
public class CglibProxyMain {

    public static void main(String[] args) {
        CglibProxyService proxyService = new CglibProxyService(new RealService());
        RealService proxy = (RealService) proxyService.getProxy();
        System.out.println(proxy);
        proxy.findUser();

        System.out.println();
        proxy.saveUser();  // 这个 save 方法会被aop拦截
    }

}
