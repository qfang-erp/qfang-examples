package com.qfang.examples.spring.ch8;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * Created by walle on 2017/4/29.
 */
public class LogBeforeAdvice implements MethodBeforeAdvice {

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("--------- 日志记录 advice -----------");
    }

}
