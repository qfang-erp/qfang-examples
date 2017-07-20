package com.qfang.examples.kafka.base;

import kafka.producer.Partitioner;
import kafka.utils.VerifiableProperties;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年10月21日
 * @since 1.0
 */
public class HashPartitioner implements Partitioner {
	
	public HashPartitioner(VerifiableProperties verifiableProperties) {}

	@Override
	public int partition(Object key, int numPartitions) {
		if(key instanceof Integer) {
			return Math.abs(Integer.valueOf(key.toString())) % numPartitions;
		}
		return Math.abs(key.hashCode() % numPartitions);
	}
	
}
