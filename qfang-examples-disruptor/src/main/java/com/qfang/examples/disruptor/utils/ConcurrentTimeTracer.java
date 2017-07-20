package com.qfang.examples.disruptor.utils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 线程安全，可以在多线程环境下
 * 
 * @author liaozhicheng
 * @date 2016年6月12日
 * @since 1.0
 */
public class ConcurrentTimeTracer implements TimeTracer {
	
	private long startTicks;
	private long endTicks;
	
	private final AtomicLong count;
	private volatile boolean end = false;
	
	private final CountDownLatch latch;

	public ConcurrentTimeTracer(long expectedCount) {
		this.count = new AtomicLong(expectedCount);
		this.latch = new CountDownLatch(1);
	}

	@Override
	public void start() {
		this.startTicks = System.currentTimeMillis();
	}

	@Override
	public long getMilliTimeSpan() {
		if(!end) {
			try {
				latch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return endTicks - startTicks;
	}

	@Override
	public void count() {
		long countCurrent = count.decrementAndGet();
		if(countCurrent == 0) {
			this.end = true;
			latch.countDown();
			endTicks = System.currentTimeMillis();
		}
	}

	@Override
	public boolean isEnd() {
		return end;
	}

	@Override
	public void waitForReached() throws InterruptedException {
		latch.await();
	}

}
