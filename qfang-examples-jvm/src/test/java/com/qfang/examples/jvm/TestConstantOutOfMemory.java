package com.qfang.examples.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试常量池内存溢出
 *
 * @author huxianyong
 * @create 2017-08-05 下午 12:39
 * 启动JVM参数 -verbose:gc  -Xmx10M -Xms10M -Xss128k -XX:+PrintGCDetails
 */
public class TestConstantOutOfMemory {
    public static void main(String[] args) {
        List<String> list=new ArrayList<>();

        int i=0;
        while(true){
            list.add(String.valueOf(i++));
           // System.out.println(" instance size:"+list.size());
        }

    }
}
