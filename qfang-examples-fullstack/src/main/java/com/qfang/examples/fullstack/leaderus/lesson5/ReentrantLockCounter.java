package com.qfang.examples.fullstack.leaderus.lesson5;

import java.util.concurrent.locks.ReentrantLock;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2016年9月8日
 * @since 1.0
 */
public class ReentrantLockCounter implements MyCounter {

	private ReentrantLock lock = new ReentrantLock();
	
	private long value;

	@Override
	public void incr() {
		lock.lock();
		try {
			value++;
		} finally {
			lock.unlock();
		}
	}

	@Override
	public long getCurValue() {
		return value;
	}
	
}
