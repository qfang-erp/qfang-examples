package com.qfang.examples.concurrent.part5;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @since 1.0
 */
public class ThreadPoolExecutorExtTest {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		int nThreads = 10;
		ExecutorService service = new ThreadPoolExecutorExt(nThreads, nThreads, 0L,
				TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
		try {
			service.submit(new ExecptionThread());
			
//			Future<?> f = service.submit(new ExecptionThread());
//			f.get();
		} finally {
			service.shutdown();
		}
	}
	
	
	private static class ThreadPoolExecutorExt extends ThreadPoolExecutor {
		
		public ThreadPoolExecutorExt(int corePoolSize, int maximumPoolSize, long keepAliveTime,
				TimeUnit unit, BlockingQueue<Runnable> workQueue) {
			super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
		}
		
		@Override
		protected void afterExecute(Runnable r, Throwable t) {
			if(r instanceof FutureTask) {
				FutureTask<?> ft = (FutureTask<?>) r;
				try {
					ft.get();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	private static class ExecptionThread implements Runnable {

		public void run() {
			throw new RuntimeException("Exception ...");
		}
		
	}
	
}
