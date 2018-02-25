package com.qfang.examples.spring.ch7.dynamicproxy.asm;

/**
 * @author: liaozhicheng.cn@163.com
 * @date: 2018-02-22
 * @since: 1.0
 */
public class MyClassLoader extends ClassLoader {

    public Class<?> defineMyClass(byte[] b, int off, int len) {
        return super.defineClass(b, off, len);
    }

}
