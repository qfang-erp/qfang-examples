package com.qfang.examples.fullstack.leaderus.lesson5;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2016年9月8日
 * @since 1.0
 */
public class SynchronizeLongCounter implements MyCounter {

	private long value;
	
	@Override
	public synchronized void incr() {
		value++;
	}

	@Override
	public long getCurValue() {
		return value;
	}
	
}
