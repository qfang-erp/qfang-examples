package com.qfang.examples.spring.other;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: liaozhicheng.cn@163.com
 * @date: 2018-02-10
 * @since: 1.0
 */
public class Resume {

    private String name;
    private String description;
    private int age;
    private List<String> phones;

    @Override
    protected Resume clone() {
        Resume clone = new Resume();
        clone.name = this.name;
        clone.description = this.description;
        clone.age = this.age;
        clone.phones = new ArrayList<>();
        clone.phones.addAll(this.phones);
        return clone;
    }
}
