package com.qfang.examples.concurrent.part5;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

import com.qfang.examples.concurrent.common.ThreadUtils;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @since 1.0
 */
public class CountDownLatchTest {
	
	private static final Random random = new Random();
	private static final int THREAD_COUNT = 10;
	
	// 总共10个线程同时运行任务，统计所有线程运行结束时，总的系统耗时
	public static void main(String[] args) throws InterruptedException {
		final CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
		
		long begin = System.currentTimeMillis();
		for(int i = 0; i < THREAD_COUNT; i++) {
			new Thread(new Job(latch), "thread-" + i).start();
		}
		latch.await();
		long end = System.currentTimeMillis();
		System.out.println(end - begin);
	}

	private static class Job implements Runnable {

		private final CountDownLatch latch;
		
		private Job(CountDownLatch latch) {
			this.latch = latch;
		}
		
		public void run() {
			try {
				ThreadUtils.sleepSilently(random.nextInt(2000));
				System.out.println(Thread.currentThread().getName() + " exit.");
			} finally {
				// 注：countDown() 方法一定要在 finally 代码块中执行，否则可能导致主线程一直等待
				latch.countDown();
			}
		}
		
	}
	
}
