package com.qfang.examples.fullstack.leaderus.lesson5;

import java.util.concurrent.atomic.AtomicLong;


/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2016年9月8日
 * @since 1.0
 */
public class AtomicLongCounter implements MyCounter {

	private AtomicLong value;
	
	public AtomicLongCounter() {
		value = new AtomicLong(0);
	}
	
	@Override
	public void incr() {
		value.incrementAndGet();
	}

	@Override
	public long getCurValue() {
		return value.get();
	}

}
