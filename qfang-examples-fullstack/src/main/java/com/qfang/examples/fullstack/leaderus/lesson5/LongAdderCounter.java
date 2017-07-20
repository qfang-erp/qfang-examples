package com.qfang.examples.fullstack.leaderus.lesson5;

import java.util.concurrent.atomic.LongAdder;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2016年9月8日
 * @since 1.0
 */
public class LongAdderCounter implements MyCounter {

	private final LongAdder value;
	
	public LongAdderCounter() {
		this.value = new LongAdder();
	}
	
	@Override
	public void incr() {
		value.increment();
	}

	@Override
	public long getCurValue() {
		return value.longValue();
	}

}
