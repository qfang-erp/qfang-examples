package com.qfang.examples.spring.ch9.homework.service;

/**
 * Created by walle on 2017/5/7.
 */
public interface AccountService {

    void transferAmount(String fromUsername, String toUsername, int amount);

}
