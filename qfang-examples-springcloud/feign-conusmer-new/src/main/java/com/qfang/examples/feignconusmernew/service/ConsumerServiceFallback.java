package com.qfang.examples.feignconusmernew.service;

import com.qfang.examples.feignconusmernew.entity.Person;
import org.springframework.stereotype.Component;

/**
 * @author huxianyong
 * @date 2018/3/7
 * @since 1.0
 */
@Component
public class ConsumerServiceFallback implements  ConsumerService {
    @Override
    public String hello() {
        return "error";
    }

    @Override
    public String hello(String name) {
        return "error";
    }

    @Override
    public Person hello(String name, Integer age) {
        return new Person("",1);
    }

    @Override
    public String hello(Person user) {
        return "error";
    }
}
