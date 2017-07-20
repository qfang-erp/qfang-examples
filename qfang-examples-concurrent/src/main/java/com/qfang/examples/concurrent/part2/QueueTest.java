package com.qfang.examples.concurrent.part2;

import java.util.Random;


/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @since 1.0
 */
public class QueueTest {
	
	public static void main(String[] args) {
//		SimpleQueueDemo<String> queue = new SynchronizedQueue<String>(5);
//		SimpleQueueDemo<String> queue = new ConditionQueue<String>(5);
		SimpleQueueDemo<String> queue = new SemaphoreQueue<String>(10);
		
		Thread ct1 = new ConsumerThread("get1:", queue);
		Thread ct2 = new ConsumerThread("get2:", queue);
		ct1.start();
		ct2.start();
		
		Thread pt1 = new ProducerThread("add1:", queue);
		Thread pt2 = new ProducerThread("add2:", queue);
		Thread pt3 = new ProducerThread("add3:", queue);
		pt1.start();
		pt2.start();
		pt3.start();
	}
	
	
	/**
	 * 消费者线程
	 * 
	 * @author liaozhicheng.cn@163.com
	 * @date 2015年10月22日
	 * @since 1.0
	 */
	private static class ConsumerThread extends Thread {
		
		private final SimpleQueueDemo<String> queue;
		
		
		private ConsumerThread(String threadName, SimpleQueueDemo<String> queue) {
			super.setName(threadName);
			this.queue = queue;
		}
		
		
		@Override
		public void run() {
			while (true) {
				queue.take();
			}
		}
		
	}
	
	
	/**
	 * 生产者线程
	 * 
	 * @author liaozhicheng.cn@163.com
	 * @date 2015年10月22日
	 * @since 1.0
	 */
	private static class ProducerThread extends Thread {
		
		private final SimpleQueueDemo<String> queue;
		
		private final Random random;
		
		
		private ProducerThread(String threadName, SimpleQueueDemo<String> queue) {
			super.setName(threadName);
			this.queue = queue;
			random = new Random();
		}
		
		
		@Override
		public void run() {
			while (true) {
				queue.put(Thread.currentThread().getName() + random.nextInt(1000));
			}
		}
		
	}
	
}
