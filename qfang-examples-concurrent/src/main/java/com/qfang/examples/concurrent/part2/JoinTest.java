package com.qfang.examples.concurrent.part2;


/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @since 1.0
 */
public class JoinTest {
	
	private static int i = 0;
	
	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				for(i = 0; i < 10000; i++);
			}
		}, "thread-01");
		
		// join 的本质还是调用 wait(0); 
		// 线程执行完毕之后系统自动调用 notifyAll();
		t1.start();
		t1.join();
		
		System.out.println(i);
	}

}
