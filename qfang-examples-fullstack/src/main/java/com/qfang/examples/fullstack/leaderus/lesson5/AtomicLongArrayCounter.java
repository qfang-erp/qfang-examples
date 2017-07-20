package com.qfang.examples.fullstack.leaderus.lesson5;

import java.util.concurrent.atomic.AtomicLongArray;
import java.util.stream.IntStream;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2016年9月8日
 * @since 1.0
 */
public class AtomicLongArrayCounter implements MyCounter {

	private final AtomicLongArray value;
	
	public AtomicLongArrayCounter(int length) {
		this.value = new AtomicLongArray(length);
	}
	
	@Override
	public void incr() {
		value.incrementAndGet(IndexHolder.get());
	}

	@Override
	public long getCurValue() {
		return IntStream.range(0, value.length())
				.mapToLong(i -> value.get(i))
				.sum();
	}

	static class IndexHolder {
		
		private static ThreadLocal<Integer> INDEX = new ThreadLocal<Integer>();
		
		public IndexHolder(int index) {
			INDEX.set(index);
		}
		
		public static int get() {
			return INDEX.get();
		}
		
	}
	
}
