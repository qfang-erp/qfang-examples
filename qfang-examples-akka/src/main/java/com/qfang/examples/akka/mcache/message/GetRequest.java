package com.qfang.examples.akka.mcache.message;

import java.io.Serializable;

/**
 * @author: liaozhicheng.cn@163.com
 * @date: 2017-11-04
 * @since: 1.0
 */
public class GetRequest implements Serializable {
    private static final long serialVersionUID = -518071165437312542L;

    private final String key;

    public GetRequest(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
