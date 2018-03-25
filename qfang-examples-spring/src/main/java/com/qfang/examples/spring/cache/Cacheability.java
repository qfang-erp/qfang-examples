package com.qfang.examples.spring.cache;

/**
 * @author: liaozhicheng.cn@163.com
 * @date: 2018-03-03
 * @since: 1.0
 */
public interface Cacheability {

    default Object get(Object[] args) {
        return null;
    }

}
