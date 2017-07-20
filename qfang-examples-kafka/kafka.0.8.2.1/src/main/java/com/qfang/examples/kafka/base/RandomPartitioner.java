package com.qfang.examples.kafka.base;

import java.util.concurrent.ThreadLocalRandom;

import kafka.producer.Partitioner;
import kafka.utils.VerifiableProperties;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年10月12日
 * @since 1.0
 */
public class RandomPartitioner implements Partitioner {
	
	// 必须保留该构造方法
	public RandomPartitioner(VerifiableProperties verifiableProperties) {}

	@Override
	public int partition(Object key, int numPartitions) {
		int index = ThreadLocalRandom.current().nextInt(numPartitions);
		System.out.format("random partition instance : %s, random index : %s \n", this, index);
		return index;
	}
	
}
