package com.qfang.examples.disruptor.utils;

import java.util.concurrent.CountDownLatch;

/**
 * TODO 
 * 
 * @author liaozhicheng
 * @date 2016年6月12日
 * @since 1.0
 */
public class CommonTimeTracer implements TimeTracer {

	private final long expectedCount;
	private final CountDownLatch latch;
	
	private long startTicks;
	private long endTicks;
	private long count = 0;
	private boolean end = false;

	public CommonTimeTracer(long expectedCount) {
		this.expectedCount = expectedCount;
		latch = new CountDownLatch(1);
	}

	@Override
	public void start() {
		this.startTicks = System.currentTimeMillis();
	}

	@Override
	public long getMilliTimeSpan() {
		return endTicks - startTicks;
	}

	@Override
	public void count() {
		if (end) {
		}
		count++;
		end = count >= expectedCount;
		if (end) {
			endTicks = System.currentTimeMillis();
			latch.countDown();
		}
	}
	
	@Override
	public boolean isEnd() {
		return false;
	}

	@Override
	public void waitForReached() throws InterruptedException {
		latch.await();
	}

}
