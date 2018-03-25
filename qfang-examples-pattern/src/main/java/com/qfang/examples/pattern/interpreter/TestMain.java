package com.qfang.examples.pattern.interpreter;

/**
 * @author: liaozhicheng.cn@163.com
 * @date: 2018-03-25
 * @since: 1.0
 */
public class TestMain {

    public static void main(String[] args) {
        boolean result = new OrNode(new AndNode(new ValueNode(false), new ValueNode(true)), new AndNode(new ValueNode(true), new NotNode(false))).interpreter();
        System.out.println(result);
    }

}
