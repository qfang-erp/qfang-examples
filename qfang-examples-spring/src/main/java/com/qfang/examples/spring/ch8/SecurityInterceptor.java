package com.qfang.examples.spring.ch8;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * Created by walle on 2017/4/29.
 */
public class SecurityInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println("======== 安全校验拦截器 ========");
        return invocation.proceed();
    }
}
