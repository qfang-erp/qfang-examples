package com.qfang.examples.kafka.base;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.stream.IntStream;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import kafka.serializer.StringEncoder;

/**
 * 注：之前测试的时候发现producer发送的消息consumer消费不到的情况（丢消息）是因为producer没有设置参数的原
 * props.put("request.required.acks", "-1");
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年10月15日
 * @since 1.0
 */
public class ProducerDemo {
	
	public static void main(String[] args) throws InterruptedException {
//		syncProducerSendOneMessage();
//		asyncProducerSendOneMessage();
//		syncProducerBatchSend();
		asyncProducerBatchSend();
//		sendMulitThread();
	}
	
	
	public static void syncProducerSendOneMessage() {
		Producer<String, String> producer = buildSyncProducer();
		sendMessage(producer, Constants.TOPIC_NAME, "1", "message : syncProducerSendOneMessage");
		producer.close();
	}
	
	public static void asyncProducerSendOneMessage() {
		Producer<String, String> producer = buildAsyncProducer();
		sendMessage(producer, Constants.TOPIC_NAME, "1", "message : asyncProducerSendOneMessage");
		producer.close();
	}
	
	public static void syncProducerBatchSend() {
		Producer<String, String> producer = buildSyncProducer();
		IntStream.range(0, 9).forEach(x -> {
			sendMessage(producer, Constants.TOPIC_NAME, x + "", "message : syncProducerBatchSend " + x);
		});
		producer.close();
	}
	
	public static void asyncProducerBatchSend() {
		Producer<String, String> producer = buildAsyncProducer();
		IntStream.range(0, 20).forEach(x -> {
			sendMessage(producer, Constants.TOPIC_NAME, x + "", "message : asyncProducerBatchSend " + x);
		});
		producer.close();
	}
	
	public static void sendMulitThread() {
		Producer<String, String> producer = buildSyncProducer();
		Random random = new Random();
		List<Thread> produceThreads = IntStream.range(0, 20).mapToObj(i -> {
			return new Thread(() -> {
				final String threadName = Thread.currentThread().getName();
				for(int j = 0; j < 10000; j++) {
					sendMessage(producer, Constants.TOPIC_NAME, random.nextInt(10000) + "", threadName + " message " + j);
				}
			});
		}).peek(Thread::start).collect(toList());
		
		produceThreads.stream().forEach(t -> {
			try {
				t.join();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		producer.close();
	}
	
	private static Producer<String, String> buildSyncProducer() {
		Properties props = new Properties();
		props.put("metadata.broker.list", Constants.BROKER_LIST);
		props.put("serializer.class", StringEncoder.class.getName());
		props.put("partitioner.class", HashPartitioner.class.getName());
		props.put("producer.type", "sync");
		// 这个参数很重要，如果不设置默认为0，也就是异步producer的方式，消息发送之后不会等待leader的ack
		props.put("request.required.acks", "-1");
		
		ProducerConfig config = new ProducerConfig(props);
		Producer<String, String> produce = new Producer<>(config);
		return produce;
	}
	
	private static Producer<String, String> buildAsyncProducer() {
		Properties props = new Properties();
		props.put("metadata.broker.list", Constants.BROKER_LIST);
		props.put("serializer.class", StringEncoder.class.getName());
		props.put("partitioner.class", HashPartitioner.class.getName());
		props.put("request.required.acks", "-1");
		props.put("producer.type", "async");  // 使用异步模式
		props.put("batch.num.messages", "3");  // 注意这里会3个消息一起提交
		props.put("queue.buffer.max.ms", "10000000");
		props.put("queue.buffering.max.messages", "1000000");
		props.put("queue.enqueue.timeout.ms", "20000000");
		
		ProducerConfig config = new ProducerConfig(props);
		Producer<String, String> produce = new Producer<>(config);
		return produce;
	}
	
	private static void sendMessage(Producer<String, String> producer, 
			String topic, String key, String message) {
		KeyedMessage<String, String> message1 = new KeyedMessage<String, String>(topic, key, message);
	    producer.send(message1);
	}
	
}
