package com.qfang.examples.pattern.strategy;

/**
 * @author: liaozhicheng.cn@163.com
 * @date: 2018-03-18
 * @since: 1.0
 */
public class TestMain {

    public static void main(String[] args) {
        LoginParams params = new LoginParams();

        LoginValidateStrategy loginValidate = new PasswordValidateStrategy();
        if(loginValidate.validate(params)) {
            // login success

        }
        throw new RuntimeException("login reject");
    }

}
