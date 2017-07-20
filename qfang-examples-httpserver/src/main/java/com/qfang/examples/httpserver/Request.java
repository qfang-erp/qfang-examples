package com.qfang.examples.httpserver;

import com.qfang.examples.httpserver.common.MethodType;

import java.util.Map;

/**
 * Created by walle on 2017/3/7.
 */
public interface Request {

//    void decode();

    MethodType getMethodType();

    /**
     * url: /test/index.html?a=b
     * @return
     */
    String getUrl();

    /**
     * uri: /test/index.html
     * @return
     */
    String getUri();

    /**
     * 返回所有的请求参数
     * @return
     */
    Map<String, Object> getRequestParams();

}
