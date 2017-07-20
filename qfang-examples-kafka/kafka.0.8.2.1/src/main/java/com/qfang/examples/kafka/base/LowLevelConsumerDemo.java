package com.qfang.examples.kafka.base;

import java.nio.ByteBuffer;

import kafka.api.FetchRequest;
import kafka.api.FetchRequestBuilder;
import kafka.javaapi.FetchResponse;
import kafka.javaapi.consumer.SimpleConsumer;
import kafka.javaapi.message.ByteBufferMessageSet;
import kafka.message.MessageAndOffset;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年11月20日
 * @since 1.0
 */
public class LowLevelConsumerDemo {
	
	public static void main(String[] args) throws Exception {
		final String topic = "test2";
		String clientId = "LowLevelConsumerClient1";
		SimpleConsumer simpleConsumer = new SimpleConsumer(
				"192.168.1.186", 9092, 6000000, 64 * 1000000, clientId);
		FetchRequest req = new FetchRequestBuilder().clientId(clientId)
								.addFetch(topic, 0, 0L, 1000000)
								.addFetch(topic, 1, 0L, 1000000)
								.addFetch(topic, 2, 0L, 1000000)
								.build();
		FetchResponse rep = simpleConsumer.fetch(req);						
		ByteBufferMessageSet messageSet = rep.messageSet(topic, 0);
		for(MessageAndOffset messageAndOffset : messageSet) {
			ByteBuffer payload = messageAndOffset.message().payload();
			long offset = messageAndOffset.offset();
			byte[] bytes = new byte[payload.limit()];
			payload.get(bytes);
			System.out.println("Offset : " + offset + ", Payload : " + new String(bytes, "UTF-8"));
		}
	}
	
}
