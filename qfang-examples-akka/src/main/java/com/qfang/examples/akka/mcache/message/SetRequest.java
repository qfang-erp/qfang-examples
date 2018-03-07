package com.qfang.examples.akka.mcache.message;

import java.io.Serializable;

/**
 * @author: liaozhicheng.cn@163.com
 * @date: 2017-11-04
 * @since: 1.0
 */
public class SetRequest implements Serializable{

    private static final long serialVersionUID = 3448892700061020988L;

    private final String key;
    private final String value;

    public SetRequest(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
