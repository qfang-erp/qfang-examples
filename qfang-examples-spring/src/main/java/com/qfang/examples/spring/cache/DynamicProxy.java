package com.qfang.examples.spring.cache;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.cglib.proxy.NoOp;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: liaozhicheng.cn@163.com
 * @date: 2018-03-03
 * @since: 1.0
 */
public class DynamicProxy implements MethodInterceptor {

    private static Map<CacheKey, Object> cache = new HashMap<>();

    private Class aClass;

    public Object getProxy(Class tClass) {
        this.aClass = tClass;

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(tClass);
        enhancer.setCallbacks(new Callback[] {NoOp.INSTANCE, this});
        enhancer.setCallbackFilter(method -> {
            if(method.getName().equals("get"))
                return 1;
            return 0;
        });
        return enhancer.create();
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        // args 是一个二维数组
        CacheKey key = new CacheKey(aClass, args);
        if(cache.containsKey(key))
            return cache.get(key);

        List<Object> argObj = Arrays.stream(args).filter(Objects::nonNull).flatMap(arg -> Arrays.stream((Object[]) arg)).collect(Collectors.toList());
        Class<?>[] argClass = argObj == null ? null : argObj.stream().map(Object::getClass).toArray(Class[]::new);

        Constructor c = aClass.getConstructor(argClass);
        Object o = c.newInstance(argObj.toArray());
        cache.put(key, o);
        return o;
    }

    class CacheKey {

        private final Class aClass;
        private final Object[] args;

        CacheKey(Class aClass, Object[] args) {
            this.aClass = aClass;
            this.args = Arrays.stream(args).filter(Objects::nonNull).flatMap(arg -> Arrays.stream((Object[]) arg)).toArray();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CacheKey cacheKey = (CacheKey) o;
            return Objects.equals(aClass, cacheKey.aClass) &&
                    Arrays.equals(args, cacheKey.args);
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(aClass);
            result = 31 * result + Arrays.hashCode(args);
            return result;
        }
    }

}
