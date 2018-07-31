package com.qfang.examples.jvm;

/**
 * 测试栈溢出
 *
 * @author huxianyong
 * @create 2017-08-05 下午 12:30
 * 启动JVM参数 -verbose:gc  -Xmx5M -Xms5M -Xss128k -XX:+PrintGCDetails
 */
public class TestStackOverFlow {
    private int counter;

    public void add(){
        counter++;
        System.out.println(counter);
        add();
    }

    public static void main(String[] args) {
        TestStackOverFlow test=new TestStackOverFlow();
        test.add();
    }
}
