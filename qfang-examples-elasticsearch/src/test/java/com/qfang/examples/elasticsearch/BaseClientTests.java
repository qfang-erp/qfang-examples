package com.qfang.examples.elasticsearch;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.qfang.examples.elasticsearch.node.ClientFactory;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年4月14日
 * @since 1.0
 */
public class BaseClientTests {
	
	protected static String INDEX_NAME = "testindex";
	protected static String TYPE_NAME = "customer";
	
	
	protected static Client client;
	
	@BeforeClass
	public static void initClient() throws UnknownHostException {
		Settings settings = Settings.builder().put("cluster.name", "elasticsearchTest").build();
		client = ClientFactory.createClient();
	}

	@AfterClass
	public static void closeClient() {
		client.close();
	}
	
}
