package com.qfang.examples.redis.performance;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年7月23日
 * @since 1.0
 */
public class PerformanceTestBase {
	
	/**
	 * 
	 * @param job 执行的具体任务
	 * @param concurrentLevel 并发的级别，多少个线程同时执行
	 * @param totalCount 总共执行多少次
	 * @throws InterruptedException 
	 */
	public static void executeTest(Job job, int concurrentLevel, long totalCount) throws InterruptedException {
		ExecutorService executorService = Executors.newFixedThreadPool(concurrentLevel);
		AtomicLong count = new AtomicLong(totalCount);
		
		for(int i = 0; i < concurrentLevel; i++) {
			executorService.submit(new JobWrapper(job, count));
		}
		
		long startTime = System.currentTimeMillis();
		long ci, ct = startTime, ti = totalCount;
		while((ci = count.get()) > 0) {
			long currentTime = System.currentTimeMillis();
			if(currentTime - ct >= 1000) {
				System.out.println(new StringBuilder().append("total count : ")
						.append(totalCount).append(", executed count : ").append(totalCount - ci).append(", tps: ").append(ti - ci).toString());
				ti = ci;
				ct = currentTime;
			}
		}
		
		long endTime = System.currentTimeMillis();
		System.out.println(new StringBuilder().append("total use time : ").append(endTime - startTime));
		
		executorService.shutdown();
	}
	
}
