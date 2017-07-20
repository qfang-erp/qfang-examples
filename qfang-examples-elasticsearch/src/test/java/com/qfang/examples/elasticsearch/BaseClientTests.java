package com.qfang.examples.elasticsearch;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
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
		Settings settings = Settings.settingsBuilder().put("cluster.name", "elasticsearchTest").build();
		client = TransportClient.builder().settings(settings).build()
							.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.1.81"), 9300))
							.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.1.82"), 9300))
							.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.1.83"), 9300));
	}

	@AfterClass
	public static void closeClient() {
		client.close();
	}
	
}
