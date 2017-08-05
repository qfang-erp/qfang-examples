package com.qfang.examples.jvm;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 测试方法区内存溢出
 *
 * @author huxianyong
 * @create 2017-08-05 下午 1:28
 * MaxMetaspaceSize
 * JVM参数 -verbose:gc -Xms10M -Xmx10M -Xss128k -XX:+PrintGCDetails -XX:MaxMetaspaceSize=20M
 * JAVA 1.7 OutOfMemoryError: PermGen space
 * JAVA 1.8 OutOfMemoryError: Metaspace
 */
public class TestMethodAreaOutOfMemory {



    public static void main(String[] args) {
        int count=1;
        Enhancer enhancer=new Enhancer();
        while(true){
            enhancer.setSuperclass(Person.class);
            enhancer.setUseCache(false);
            enhancer.setCallback(new MethodInterceptor() {
                @Override
                public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                    System.out.println("this is Proxy!");
                    return methodProxy.invokeSuper(o,objects);
                }
            });
            enhancer.create();
            System.out.println("create instantce:"+(count++));
        }
    }
}
