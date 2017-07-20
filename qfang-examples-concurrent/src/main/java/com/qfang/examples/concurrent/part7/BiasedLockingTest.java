package com.qfang.examples.concurrent.part7;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CountDownLatch;

import com.qfang.examples.concurrent.common.ThreadUtils;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @since 1.0
 */
public class BiasedLockingTest {
	
	// 每个线程执行新增的总次数
	private static final int TOTAL = 1000;
	// 总共启用的线程数
	private static final int THREAD_NUM = 5;
	
	
	public static void main(String[] args) throws InterruptedException {
		for(int i = 0; i < 10; i++) {
			// 每个用例执行10次，可以看出平均时间
			execute(i);
		}
	}
	
	
	public static void execute(int num) throws InterruptedException {
		Collection<Boolean> collection = null;
		collection = Collections.synchronizedList(new ArrayList<Boolean>(THREAD_NUM * TOTAL));
		
		final CountDownLatch latch = new CountDownLatch(THREAD_NUM);
		long begin = System.currentTimeMillis();
		
		for(int i = 0; i < THREAD_NUM; i++) {
			Thread t = new Thread(new Job(latch, collection), "thread-" + num + "-" + i);
			t.start();
		}
		
		latch.await();
		long end = System.currentTimeMillis();
		long time = end - begin;
		
		System.out.println("第 " + num + " 次计算，总计添加次数：" + collection.size() + "，总计耗时： " + time);
	}
	
	
	private static class Job implements Runnable {
		
		private final CountDownLatch latch;
		
		private final Collection<Boolean> collection;
		
		Job(CountDownLatch latch, Collection<Boolean> collection) {
			this.latch = latch;
			this.collection = collection;
		}

		@Override
		public void run() {
			try {
				long begin = System.currentTimeMillis();
				
				int count = 0;
				while(count++ < TOTAL) {
					ThreadUtils.sleepSilently(10);
					collection.add(Boolean.TRUE);
				}
				
				long end = System.currentTimeMillis();
				long time = end - begin;
				System.out.println(Thread.currentThread().getName() + " 运行耗时：" + time);
			} finally {
				latch.countDown();
			}
		}
		
	}

}
