package com.qfang.examples.concurrent.part5;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import com.qfang.examples.concurrent.common.ThreadUtils;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @since 1.0
 */
public class TryLockWithTimeOut {
	
	private static final ReentrantLock lock = new ReentrantLock();
	
	public static void main(String[] args) {
		// 10个线程同时尝试获取同一把锁，超时时间1s，获取到锁的线程睡眠2s，所以只会有一个线程获取成功，其他线程都会获取失败
		for(int i = 0; i < 10; i++) {
			new Thread(new TestThread(), "thread" + i).start();
		}
	}

	
	private static class TestThread implements Runnable {

		public void run() {
			try {
				if(lock.tryLock(1000, TimeUnit.MILLISECONDS)) {
					try {
						ThreadUtils.sleepSilently(2000);
						System.out.println(Thread.currentThread().getName() + " get lock success..");
					} finally {
						lock.unlock();
					}
				}
				
				System.out.println(Thread.currentThread().getName() + " get lock failed...");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
