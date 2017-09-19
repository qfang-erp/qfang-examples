package com.qfang.examples;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author huxianyong
 * @date 2017/8/16
 * @since 1.0
 */
public class Person {
    private String number;
    private String name;
    private boolean work;

    public void work(){
        work=true;
        System.out.println(name+ " is working！");
        try {
            Thread.sleep(new Random().nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void close(){
        work=false;
        System.out.println(" stop work！");
    }

    public Person(String number, String name) {
        this.number = number;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", work=" + work +
                '}';
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
