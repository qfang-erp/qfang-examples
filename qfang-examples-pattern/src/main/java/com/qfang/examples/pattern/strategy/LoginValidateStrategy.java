package com.qfang.examples.pattern.strategy;

/**
 * @author: liaozhicheng.cn@163.com
 * @date: 2018-03-18
 * @since: 1.0
 */
public interface LoginValidateStrategy {

    boolean validate(LoginParams params);

}
