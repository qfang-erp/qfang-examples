package com.qfang.examples.elasticsearch.node;

import java.net.UnknownHostException;

import org.elasticsearch.client.Client;

public class TransportTest {

	public static void main(String[] args) throws UnknownHostException {
		// TODO Auto-generated method stub
//		Client client = TransportClient.builder().build()
//		        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.1.110"), 9300));

		Client client =ClientFactory.createClient();
		// on shutdown
		client.close();
	}

}
