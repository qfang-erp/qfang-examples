package com.qfang.examples.spring.ch8.annotationconfig;

import org.springframework.stereotype.Component;

/**
 * Created by walle on 2017/4/30.
 */
@Component
public class UserService {

    public void findUser() {
        System.out.println("find user");
    }

    public  void saveUser() {
        System.out.println("save user");
    }

    @MockTransaction(name = "mock transaction")
    public void updateUser() {
        System.out.println("update user");
    }

}
