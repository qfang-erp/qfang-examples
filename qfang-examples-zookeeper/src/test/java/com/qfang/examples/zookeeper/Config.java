package com.qfang.examples.zookeeper;

import java.nio.charset.Charset;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2016年7月14日
 * @since 1.0
 */
public class Config {
	
	public static final String ZK_SERVER = "192.168.1.131:2181";

	public static final String ZK_SERVER_CLUSTER = "192.168.1.131:2181,192.168.1.131:2281,192.168.1.131:2381";
//	public static final String ZK_SERVER_CLUSTER = "127.0.0.1:2181";

    // 默认超时时间 5s
    public static final int DEFUALT_TIME_OUT = 5000;

    public static final String DEFAULT_TEST_PATH = "/zk_examples";
    public static final String DEFAULT_NODE_CONTENT = "init";

    public static final String UTF_8 = "UTF-8";
    public static final Charset UTF_8_CHARSET = Charset.forName(UTF_8);
    public static final byte[] DEFAULT_NODE_DATA = DEFAULT_NODE_CONTENT.getBytes(UTF_8_CHARSET);



}
