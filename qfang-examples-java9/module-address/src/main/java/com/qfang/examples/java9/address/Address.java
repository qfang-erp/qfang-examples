package com.qfang.examples.java9.address;

/**
 * @author: liaozhicheng.cn@163.com
 * @date: 2018-01-14
 * @since: 1.0
 */
public class Address {

    private final String address;

    public Address(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Address{" +
                "address='" + address + '\'' +
                '}';
    }
}
