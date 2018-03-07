package com.qfang.examples.java9.user;

import com.qfang.examples.java9.address.Address;

/**
 * @author: liaozhicheng.cn@163.com
 * @date: 2018-01-14
 * @since: 1.0
 */
public class User {

    private final Address address;

    User(Address address) {
        this.address = address;
    }

    public static void main(String[] args) {
        User user = new User(new Address("test"));
        System.out.println(user.address);
    }

}
