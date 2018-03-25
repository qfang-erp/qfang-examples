package com.qfang.examples.pattern.strategy;

/**
 * @author: liaozhicheng.cn@163.com
 * @date: 2018-03-18
 * @since: 1.0
 */
public class QRCodeValidateStrategy implements LoginValidateStrategy {

    @Override
    public boolean validate(LoginParams params) {
        return false;
    }
}
