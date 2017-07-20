package com.qfang.examples.concurrent.part2;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @since 1.0
 */
public class InterruptTest {
	
	public static void main(String args[]) throws Exception {
		testExample1();
		// testExample2();
		// testExample3();
	}
	
	
	//////// ------------------
	// #interrupt() 并不会中断一个正在运行的线程
	static void testExample1() throws InterruptedException {
		Thread thread = new Thread(new InterruptExample1());
		System.out.println("Starting thread...");
		thread.start();
		Thread.sleep(3000);
		System.out.println("Interrupting thread...");
		
		thread.interrupt();  // 发出中断信号之后，线程还会继续运行
		Thread.sleep(3000);
		System.out.println("Stopping application...");
	}
	
	private static class InterruptExample1 implements Runnable {
		
		boolean stop = false;
		
		
		@Override
		public void run() {
			while (!stop) {
				System.out.println("Thread is running...");
				long time = System.currentTimeMillis();
				while ((System.currentTimeMillis() - time < 1000)) {}
			}
			System.out.println("Thread exiting under request...");
		}
		
	}
	
	
	////////// --------------------
	// 中断线程推荐的方式是，使用共享变量发出信号，告诉线程必须停止正在运行的任务。线程必须周期性的检查这一变量，然后有秩序地中止任务
	static void testExample2() throws InterruptedException {
		InterruptExample2 r = new InterruptExample2();
		Thread thread = new Thread(r);
		System.out.println("Starting thread...");
		thread.start();
		Thread.sleep(3000);
		System.out.println("Asking thread to stop...");
		
		r.stop = true;
		Thread.sleep(3000);
		System.out.println("Stopping application...");
	}
	
	private static class InterruptExample2 implements Runnable {
		
		volatile boolean stop = false;
		
		
		@Override
		public void run() {
			while (!stop) {
				System.out.println("Thread is running...");
				long time = System.currentTimeMillis();
				while ((System.currentTimeMillis() - time < 1000) && (!stop)) {}
			}
			System.out.println("Thread exiting under request...");
		}
	}
	
	
	////////// --------------------
	// 如果线程处于阻塞状态（sleep,join,wait），那么即使设置共享变量，它也没办法检测到该状态的改变
	// 所以需要结合 interrupt 及共享变量的方式来中断线程
	static void testExample3() throws InterruptedException {
		InterruptExample3 r = new InterruptExample3();
		Thread thread = new Thread(r);
		System.out.println("Starting thread...");
		thread.start();
		Thread.sleep(3000);
		System.out.println("Asking thread to stop...");
		
		r.stop = true;// 如果线程阻塞，将不会检查此变量
		thread.interrupt();
		Thread.sleep(3000);
		System.out.println("Stopping application...");
	}
	
	private static class InterruptExample3 implements Runnable {
		
		volatile boolean stop = false;
		
		
		public void run() {
			while (!stop) {
				System.out.println("Thread running...");
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// 处理中断逻辑
					System.out.println("Thread interrupted...");
				}
			}
			System.out.println("Thread exiting under request...");
		}
	}
	
}
