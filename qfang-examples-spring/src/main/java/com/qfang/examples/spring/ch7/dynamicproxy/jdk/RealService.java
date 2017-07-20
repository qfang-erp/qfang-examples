package com.qfang.examples.spring.ch7.dynamicproxy.jdk;

/**
 * Created by walle on 2017/4/20.
 */
public class RealService implements IService {

    @Override
    public void sayHello() {
        System.out.println("hello world");
    }

}
