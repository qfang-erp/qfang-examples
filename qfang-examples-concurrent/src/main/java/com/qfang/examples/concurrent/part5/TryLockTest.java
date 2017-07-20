package com.qfang.examples.concurrent.part5;

import java.util.concurrent.locks.ReentrantLock;

import com.qfang.examples.concurrent.common.ThreadUtils;


/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @since 1.0
 */
public class TryLockTest {

	private static ReentrantLock lock1 = new ReentrantLock();
	private static ReentrantLock lock2 = new ReentrantLock();
	
	
	public static void main(String[] args) throws InterruptedException {
		// 但是构建程序的时候非常不建议出现这种不一致的锁获取顺序
		Thread1 t1 = new Thread1();
		Thread2 t2 = new Thread2();
		
		t1.start();
		t2.start();
		
		t1.join();
		t2.join();
		
		System.out.println("exit..");
	}

	
	private static class Thread1 extends Thread {

		@Override
		public void run() {
			while(true) {
				if(lock1.tryLock()) {
					try {
						if(lock2.tryLock()) {
							try {
								// do something...
								ThreadUtils.sleepSilently(500);
								System.out.println("Thread1 exit...");
								return;
							} finally {
								lock2.unlock();
							}
						}
					} finally {
						lock1.unlock();
					}
				}
				
				// if(elapsedTime > stopTime) return;
				// 注：这里可以加上一个耗时检测，如果尝试时间过长，可以主动退出，避免出现活锁
			}
		}
		
	}
	
	private static class Thread2 extends Thread {

		@Override
		public void run() {
			while(true) {
				if(lock2.tryLock()) {
					try {
						if(lock1.tryLock()) {
							try {
								// do something...
								ThreadUtils.sleepSilently(500);
								System.out.println("Thread2 exit...");
								return;
							} finally {
								lock1.unlock();
							}
						}
					} finally {
						lock2.unlock();
					}
				}
			}
		}
		
	}
	
}
