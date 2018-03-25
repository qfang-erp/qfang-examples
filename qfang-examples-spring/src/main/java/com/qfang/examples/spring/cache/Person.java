package com.qfang.examples.spring.cache;

/**
 * @author: liaozhicheng.cn@163.com
 * @date: 2018-03-03
 * @since: 1.0
 */
public class Person implements Cacheability {

    private String name;

    public Person() {
    }

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
