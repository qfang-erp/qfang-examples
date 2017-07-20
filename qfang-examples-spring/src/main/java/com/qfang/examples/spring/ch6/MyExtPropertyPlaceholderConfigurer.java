package com.qfang.examples.spring.ch6;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * Created by walle on 2017/4/16.
 */
public class MyExtPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

    private static final String PASSWORD = "password";

    @Override
    protected String convertProperty(String propertyName, String propertyValue) {
        if(PASSWORD.equals(propertyName)) {
            return MD5Utils.encrypted(propertyValue);
        }
        return super.convertProperty(propertyName, propertyValue);
    }

}
