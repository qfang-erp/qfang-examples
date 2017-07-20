package com.qfang.examples.fullstack.leaderus.lesson5;

import java.util.stream.LongStream;

import com.qfang.examples.fullstack.leaderus.lesson5.AtomicLongArrayCounter.IndexHolder;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2016年9月9日
 * @since 1.0
 */
public class LongArrayCounter implements MyCounter {
	
	private long[] array;
	
	public LongArrayCounter(int length) {
		array = new long[length];
	}

	@Override
	public void incr() {
		// 每个线程只在一个指定的元素上累加
		int index = IndexHolder.get();
		long o = array[index];
		array[index] = o + 1;
	}

	@Override
	public long getCurValue() {
		return LongStream.of(array).sum();
	}

}
