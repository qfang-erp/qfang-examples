package com.qfang.examples.concurrent.part5;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.qfang.examples.concurrent.common.ThreadUtils;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @since 1.0
 */
public class ReadWriteLockTest {
	
	private static final ReadWriteLock rwLock = new ReentrantReadWriteLock();
	private static final Lock readLock = rwLock.readLock();
	private static final Lock writeLock = rwLock.writeLock();
	
//	private static final ReentrantLock rLock = new ReentrantLock();
	
	private static final int READ_THREAD_COUNT = 20;
	private static final int WRITE_THREAD_COUNT = 2;
	
	private static int count = 0;
	
	// 测试采用读写锁和使用普通锁的情况下，20个读线程，2个写线程（读大于写）的情况下性能的提升
	public static void main(String[] args) throws InterruptedException {
		CountDownLatch rLatch = new CountDownLatch(READ_THREAD_COUNT);
		CountDownLatch wLatch = new CountDownLatch(WRITE_THREAD_COUNT);
		
		long start = System.currentTimeMillis();
		
		for(int i = 0; i < READ_THREAD_COUNT; i++) {
			ReadJob job = new ReadJob(readLock, rLatch);
//			ReadJob job = new ReadJob(rLock, rLatch);
			
			new Thread(job, "readThread" + i).start();
		}
		
		for(int i = 0; i < WRITE_THREAD_COUNT; i++) {
			WriteJob job = new WriteJob(writeLock, wLatch);
//			WriteJob job = new WriteJob(rLock, wLatch);
			
			new Thread(job, "writeThread" + i).start();
		}
		
		rLatch.await();
		wLatch.await();
		
		long end = System.currentTimeMillis();
		System.out.println("elapsedTime: " + (end - start));
		System.out.println(count);
	}

	
	private static class ReadJob implements Runnable {

		private final Lock lock;
		private final CountDownLatch latch;
		ReadJob(Lock lock, CountDownLatch latch) {
			this.lock = lock;
			this.latch = latch;
		}
		
		public void run() {
			lock.lock();
			try {
				ThreadUtils.sleepSilently(500);
				latch.countDown();
			} finally {
				lock.unlock();
			}
		}
		
	}
	
	
	private static class WriteJob implements Runnable {
		
		private final Lock lock;
		private final CountDownLatch latch;
		WriteJob(Lock lock, CountDownLatch latch) {
			this.lock = lock;
			this.latch = latch;
		}

		public void run() {
			lock.lock();
			try {
				ThreadUtils.sleepSilently(50);
				count++;
				latch.countDown();
			} finally {
				lock.unlock();
			}
		}
		
	}
	
}
