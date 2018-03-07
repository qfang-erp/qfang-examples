package com.qfang.examples.akka.mcache.message;

import java.io.Serializable;

/**
 * @author: liaozhicheng.cn@163.com
 * @date: 2017-11-04
 * @since: 1.0
 */
public class KeyNotFoundException extends Exception implements Serializable {
    private static final long serialVersionUID = 6167868779659999862L;

    private final String key;

    public KeyNotFoundException(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
