package com.qfang.examples.spring.ch7.beanPostProcessor;

import net.sf.cglib.proxy.*;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Method;

/**
 * Created by walle on 2017/4/23.
 */
public class LogAopBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        if(Matcher.isMatch(beanClass.getSimpleName())) {
            LogAopProxy proxy = new LogAopProxy();
            return proxy.getProxyObj(bean);
        }
        return bean;
    }

    private static class Matcher {

        private static final String regex = "\\w*Service#save\\w*";

        private static String getBeanNameRegex() {
            return regex.split("#")[0];
        }

        private static String getMethodRegex() {
            return regex.split("#")[1];
        }

        private static boolean isMatch(String beanName) {
            return beanName.matches(getBeanNameRegex());
        }

    }

    private static class LogAopProxy implements MethodInterceptor {

        public Object target;

        public Object getProxyObj(Object target) {
            this.target = target;

            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(target.getClass());
            enhancer.setCallbacks(new Callback[] {NoOp.INSTANCE, this});
            enhancer.setCallbackFilter(method -> {
                if(method.getName().matches(Matcher.getMethodRegex()))
                    return 1;
                return 0;
            });
            return enhancer.create();
        }

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            System.out.println("LogAopProxy: method invoke log ....");
            return methodProxy.invoke(target, objects);
        }
    }

}
