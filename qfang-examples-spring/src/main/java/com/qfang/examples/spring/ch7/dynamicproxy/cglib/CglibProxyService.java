package com.qfang.examples.spring.ch7.dynamicproxy.cglib;

import net.sf.cglib.proxy.*;

import java.lang.reflect.Method;

/**
 * Created by walle on 2017/4/20.
 */
public class CglibProxyService implements MethodInterceptor {

    private Object target;

    public CglibProxyService(Object target) {
        this.target = target;
    }

    public Object getProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallbacks(new Callback[] {NoOp.INSTANCE, this});
        enhancer.setCallbackFilter(method -> {
            if(method.getName().startsWith("save"))  // 拦截所有以 save 开头的方法
                return 1;
            return 0;
        });
        return enhancer.create();
    }

    @Override
    public Object intercept(Object proxyObj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("CglibProxyService: method intercept log ....");
        return methodProxy.invoke(target, args);
    }

}
