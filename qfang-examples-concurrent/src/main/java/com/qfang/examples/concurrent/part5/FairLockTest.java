package com.qfang.examples.concurrent.part5;

import java.util.Collection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @since 1.0
 */
public class FairLockTest {
	
	
	// 模拟5个线程在公平锁和非公平锁的情况下获取同一把锁，每个线程都尝试获取5次
	// 可以看到非公平锁的情况下同一个线程连续获取锁的概率很大，而公平锁的则会按申请锁的顺序获取锁
	public static void main(String[] args) throws InterruptedException {
		final ReentrantLock lock = new MyReentrantLock();
//		final ReentrantLock lock = new MyReentrantLock(true);
		final CountDownLatch latch = new CountDownLatch(5);
		for(int i = 0; i < 5; i++) {
			new Thread(new Job(lock, latch), i + "").start();;
		}
		
		latch.await();
	}
	
	
	private static final class Job implements Runnable {
		
		private final ReentrantLock lock;
		private final CountDownLatch latch;
		
		private Job(ReentrantLock lock, CountDownLatch latch) {
			this.lock = lock;
			this.latch = latch;
		}
		
		public void run() {
			try {
				for(int i = 0; i < 5; i++) {
					lock.lock();
					try {
						System.out.println("Lock by:" + Thread.currentThread().getName() 
								+ " and " + ((MyReentrantLock) lock).getQueuedThreadNames()
								+ " waits.");
					} finally {
						lock.unlock();
					}
				}
			} finally {
				latch.countDown();
			}
		}
	}
	
	
	private static final class MyReentrantLock extends ReentrantLock {
		
		private static final long serialVersionUID = 6427751225467341600L;
		
		private MyReentrantLock() {
			super();
		}
		
		private MyReentrantLock(boolean fair) {
			super(fair);
		}
		
		public String getQueuedThreadNames() {
			Collection<Thread> ts = super.getQueuedThreads();
			StringBuilder sb = new StringBuilder("[ ");
			for(Thread t : ts) {
				sb.append(t.getName()).append(",");
			}
			return sb.deleteCharAt(sb.length() - 1).append(" ]").toString();
		}
		
	}

}
