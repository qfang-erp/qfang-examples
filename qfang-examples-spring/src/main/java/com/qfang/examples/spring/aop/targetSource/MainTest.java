package com.qfang.examples.spring.aop.targetSource;

import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.target.SingletonTargetSource;

/**
 * @author: liaozhicheng.cn@163.com
 * @date: 2018-04-03
 * @since: 1.0
 */
public class MainTest {

    public static void main(String[] args) {
        TargetBean target = new TargetBean();
        TargetSource targetSource = new SingletonTargetSource(target);
        // 使用SpringAOP框架的代理工厂直接创建代理对象
        TargetBean proxy = (TargetBean) ProxyFactory.getProxy(targetSource);
        // 这里会在控制台打印：com.lixin.aopdemo.TargetBean$$EnhancerBySpringCGLIB$$767606b3
        System.out.println(proxy.getClass().getName());
    }

}
