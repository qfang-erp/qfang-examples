package com.qfang.examples.concurrent.part5;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @since 1.0
 */
public class SemaphoreTest {
	
	// 总共 20 个线程申请许可，但只有 5 个信号量许可，所有每次只能有 5 个线程完成，其他线程必须等待
	private static final int THREAD_COUNT = 20;
	private static final Semaphore s = new Semaphore(5);
	
	public static void main(String[] args) {
		final ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
		for(int i = 0; i < THREAD_COUNT; i++) {
			executor.submit(new Runnable() {
				public void run() {
					try {
						s.acquire();  // 也可以让一个线程获取多个许可
						System.out.println(Thread.currentThread().getName() + " working ..");
						TimeUnit.MILLISECONDS.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					} finally {
						s.release();
					}
				}
			}, "thread-" + i);
		}
		
		executor.shutdown();
	}

}
