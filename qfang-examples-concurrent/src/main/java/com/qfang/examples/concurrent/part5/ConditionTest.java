package com.qfang.examples.concurrent.part5;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @since 1.0
 */
public class ConditionTest {
	
	private static final ReentrantLock lock = new ReentrantLock();
	private static final Condition condition = lock.newCondition();
	
	private static volatile boolean flag = false;


	// condition#await(), condition#signal(), condition#signalAll() 用于协调多个线程之间的调度（例如：等待一个公共的条件）
	public static void main(String[] args) throws InterruptedException {
		// 3个线程启动之后检查 flag 标志位，如果为 false 则进行等待
		for(int i = 0; i < 3; i++) {
			new Thread(new Job(), "thread-" + i).start();
		}
		
		// 主线程休眠 2s 之后，获取锁，并开始唤醒所有的等待线程，这里尝试唤醒3次，但是只有第三次唤醒的时候才会将 flag 标志位置为 true
		// 所以只有第三次唤醒时才会真正让子线程执行完成
		TimeUnit.MILLISECONDS.sleep(2000);
		for(int i = 0; i < 3; i++) {
			TimeUnit.MILLISECONDS.sleep(1000);
			lock.lock();
			try {
				condition.signalAll();
				System.out.println("singal all " + i);
				
				if(i == 2) {
					flag = true;
				}
			} finally {
				lock.unlock();
			}
		}
	}

	
	private static final class Job implements Runnable {

		public void run() {
			lock.lock();
			try {
				System.out.println(Thread.currentThread().getName() + " get lock.");
				
				// 子线程被唤醒之后继续检查 flag 标志位，是否为true，如果不为 true 继续进入等待状态
				while(!flag) {
					System.out.println(Thread.currentThread().getName() + " await.");
					condition.await();
				}
				
				System.out.println(Thread.currentThread().getName() + " exit.");
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}
		
	}
}
