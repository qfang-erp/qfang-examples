package com.qfang.examples.spring.cache;

/**
 * @author: liaozhicheng.cn@163.com
 * @date: 2018-03-03
 * @since: 1.0
 */
public class DynamicProxyFactory {

    public static <T> T getProxy(Class<T> tClass) {
        DynamicProxy proxy = new DynamicProxy();
        return (T) proxy.getProxy(tClass);
    }

}
