package com.qfang.examples.spring.ioc.property;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Created by walle on 2017/4/16.
 */
class MD5Utils {


    /***
     * MD5加码 生成32位md5码
     */
    public static String encrypted(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(text.getBytes());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String text) {
        return "123456";  // 简单实现，返回默认的密码
    }

}
