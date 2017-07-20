package com.qfang.examples.redis.performance;

import java.util.concurrent.atomic.AtomicLong;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年7月23日
 * @since 1.0
 */
class JobWrapper implements Runnable {
	
	private final Job job;
	private final AtomicLong count;
	
	public JobWrapper(Job job, AtomicLong count) {
		this.job = job;
		this.count = count;
	}
	
	@Override
	public void run() {
		String threadName = Thread.currentThread().getName();
		
		long i;
		while((i = count.getAndDecrement()) > 0) {
			job.execute(i, threadName);
		}
	}
	
}
