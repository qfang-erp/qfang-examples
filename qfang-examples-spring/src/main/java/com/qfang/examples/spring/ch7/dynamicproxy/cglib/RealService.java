package com.qfang.examples.spring.ch7.dynamicproxy.cglib;

/**
 * Created by walle on 2017/4/20.
 */
public class RealService {

    public void saveUser() {
        System.out.println("save user");
    }

    public void findUser() {
        System.out.println("find user");
    }

}
