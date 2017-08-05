package com.qfang.examples.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试堆内存溢出
 * @author huxianyong
 * @create 2017-08-05 下午 12:05
 * 启动JVM参数 -verbose:gc  -Xmx5M -Xms5M -Xss128k -XX:+PrintGCDetails
 */
public class TestHeapOutOfMemory {
    public static void main(String[] args) {
        List<Person> list=new ArrayList<>();

        while(true){
            list.add(new Person());
            System.out.println("instance count:"+list.size());
        }


    }
}

class Person{
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
