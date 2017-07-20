package com.qfang.examples.httpserver;

import java.io.IOException;

/**
 * Created by walle on 2017/3/7.
 */
public interface Response {

    byte[] toBytes() throws IOException;

}
