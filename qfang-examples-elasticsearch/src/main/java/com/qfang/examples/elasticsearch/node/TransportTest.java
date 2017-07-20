package com.qfang.examples.elasticsearch.node;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

public class TransportTest {

	public static void main(String[] args) throws UnknownHostException {
		// TODO Auto-generated method stub
//		Client client = TransportClient.builder().build()
//		        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.1.110"), 9300));
		
		Client client = TransportClient.builder().build()
		        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("169.254.135.217"), 9300));

		// on shutdown
		client.close();
	}

}
