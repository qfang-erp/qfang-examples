package com.qfang.examples.concurrent.part5;

import java.util.concurrent.locks.ReentrantLock;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @since 1.0
 */
public class ReentrantLockTest1 {
	
	private static ReentrantLock lock = new ReentrantLock();
	private static int count = 0;
	
	public static void main(String[] args) throws InterruptedException {
		Thread ct1 = new Thread(new CountThread(), "ct1");
		Thread ct2 = new Thread(new CountThread(), "ct2");
		
		ct1.start();
		ct2.start();
		
		ct1.join();
		ct2.join();
		
		System.out.println(count);
	}
	
	
	private static class CountThread implements Runnable {
		
		public void run() {
			lock.lock();
//			lock.lock();  // 可重入特性，可以多次获取这个锁
			try {
				for(int i = 0; i < 100000; i++) {
					count++;
				}
			} finally {
				lock.unlock();
//				lock.unlock();  // 如果获取了两次，则这里也需要释放两次
			}
		}
		
	}

}
