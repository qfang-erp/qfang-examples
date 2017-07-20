package com.qfang.examples.concurrent.part5;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @since 1.0
 */
public class LockInterruptiblyTest {
	
	// 模拟一个因为锁获取顺序交叉而导致的死锁场景
	// 线程1获取 lock1，后申请获取 lock2
	// 线程2则相反，先获取 lock2，然后申请获取 lock1
	// 线程1一直等到 lock2，线程2一直等待 lock1，出现死锁，如果两个线程都是使用 lock.lock() 方法获取锁的话，这个死锁基本不可能解开
	private static ReentrantLock lock1 = new ReentrantLock();
	private static ReentrantLock lock2 = new ReentrantLock();
	
	
	public static void main(String[] args) throws InterruptedException {
		Thread1 t1 = new Thread1();
		Thread2 t2 = new Thread2();
		
		t1.start();
		t2.start();
		
		t1.join(3000);
		t2.join(3000);
		
		t2.interrupt();
		
		System.out.println("exit..");
	}

	
	private static class Thread1 extends Thread {

		@Override
		public void run() {
			try {
				lock1.lockInterruptibly();
//				lock1.lock();
				
				TimeUnit.MICROSECONDS.sleep(2000);
				
				lock2.lockInterruptibly();
//				lock2.lock();
				
				System.out.println("Thread1 exit..");
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				if(lock1.isHeldByCurrentThread())
					lock1.unlock();
				if(lock2.isHeldByCurrentThread())
					lock2.unlock();
			}
		}
		
	}
	
	private static class Thread2 extends Thread {

		@Override
		public void run() {
			try {
				lock2.lockInterruptibly();
//				lock2.lock();
				
				TimeUnit.MICROSECONDS.sleep(2000);
				
				lock1.lockInterruptibly();
//				lock1.lock();
				
				System.out.println("Thread2 exit..");
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				if(lock2.isHeldByCurrentThread())
					lock2.unlock();
				if(lock1.isHeldByCurrentThread())
					lock1.unlock();
			}
		}
		
	}
	
	
}
