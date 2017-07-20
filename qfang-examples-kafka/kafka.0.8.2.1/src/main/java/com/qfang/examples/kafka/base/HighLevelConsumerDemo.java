package com.qfang.examples.kafka.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年10月15日
 * @since 1.0
 */
public class HighLevelConsumerDemo {
	
	public static void main(String[] args) {
		args = new String[] { Constants.ZK_SERVER, Constants.TOPIC_NAME, "group1", "consumer1" };
		if (args == null || args.length != 4) {
			System.err.println("Usage:\n\tjava -jar kafka_consumer.jar ${zookeeper_list} ${topic_name} ${group_name} ${consumer_id}");
			System.exit(1);
		}
		String zk = args[0];
		String topic = args[1];
		String groupid = args[2];
		String consumerid = args[3];
		Properties props = new Properties();
		props.put("zookeeper.connect", zk);
		props.put("group.id", groupid);
		props.put("client.id", "test");
		props.put("consumer.id", consumerid);
		props.put("auto.offset.reset", "smallest");
		props.put("auto.commit.enable", "true");
		props.put("auto.commit.interval.ms", "60000");

		ConsumerConfig consumerConfig = new ConsumerConfig(props);
		ConsumerConnector consumerConnector = Consumer.createJavaConsumerConnector(consumerConfig);

		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put(topic, 1);
		Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumerConnector.createMessageStreams(topicCountMap);

		KafkaStream<byte[], byte[]> stream1 = consumerMap.get(topic).get(0);
		ConsumerIterator<byte[], byte[]> interator = stream1.iterator();
		while (interator.hasNext()) {
			MessageAndMetadata<byte[], byte[]> messageAndMetadata = interator.next();
			String message = String.format(
					"Topic:%s, GroupID:%s, Consumer ID:%s, PartitionID:%s, Offset:%s, Message Key:%s, Message Payload: %s",
					messageAndMetadata.topic(), groupid, consumerid, messageAndMetadata.partition(),
					messageAndMetadata.offset(), new String(messageAndMetadata.key() != null ? messageAndMetadata.key() : "".getBytes()),
					new String(messageAndMetadata.message()));
			System.out.println(message);
		}
	}
	
}
