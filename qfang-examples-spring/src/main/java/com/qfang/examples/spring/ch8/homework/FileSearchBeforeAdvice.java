package com.qfang.examples.spring.ch8.homework;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * Created by walle on 2017/4/30.
 */
public class FileSearchBeforeAdvice implements MethodBeforeAdvice {

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("============ log before search ==============");
    }

}
