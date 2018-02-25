package com.qfang.examples.spring.ch8.annotationconfig;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * Created by walle on 2017/4/29.
 */
@Aspect
@Component
public class SecurityInterceptor {

    @Pointcut("execution(public * com.qfang.examples.spring.ch8.annotationconfig.*Service.find*(..))")
    public void myPointcut() {
    }

    @Before("myPointcut()")
    public void before(JoinPoint joinPoint) throws Throwable {
        System.out.println("======== 安全校验拦截器:before ========");
        System.out.println(joinPoint.getSignature());  // 获取方法签名
    }

    @After("myPointcut()")
    public void after(JoinPoint joinPoint) {
        System.out.println("======== 安全校验拦截器:after ========");
    }

    @AfterReturning(value = "myPointcut()", returning = "returnValue")
    public void afterReturn(JoinPoint point, Object returnValue) {
        System.out.println("======== 安全校验拦截器:afterReturn ========");
    }

    @AfterThrowing("myPointcut()")
    public void afterThrowing() {
        System.out.println("======== 安全校验拦截器:afterThrowing ========");
    }

    @Around("execution(public * com.jaf.examples.spring.ch8.annotationconfig.*Service.save*(..))")
    public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("======== 安全校验拦截器:around ========");
        proceedingJoinPoint.proceed();  // 只有 around 方法才有 ProceedingJoinPoint 参数
    }

    @Around("@annotation(mockTransaction)")
    public void aroundAnnotation(ProceedingJoinPoint proceedingJoinPoint, MockTransaction mockTransaction)
            throws Throwable {
        System.out.println("======== 安全校验拦截器:aroundAnnotation ========");
        System.out.println(mockTransaction.name());
        proceedingJoinPoint.proceed();
    }

}
