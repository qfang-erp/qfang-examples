package com.qfang.examples.pattern.strategy;

/**
 * @author: liaozhicheng.cn@163.com
 * @date: 2018-03-18
 * @since: 1.0
 */
class LoginParams {

    private String username;
    private String password;

    private String randomCode;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRandomCode() {
        return randomCode;
    }

    public void setRandomCode(String randomCode) {
        this.randomCode = randomCode;
    }
}
