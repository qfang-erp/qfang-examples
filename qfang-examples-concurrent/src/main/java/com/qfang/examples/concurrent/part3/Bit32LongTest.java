package com.qfang.examples.concurrent.part3;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @since 1.0
 */
public class Bit32LongTest {
	
	// 写一个程序，证明在32位JVM上，long不是原子操作
	// long和double占用的字节数都是8，也就是64bits。
	// 在32位操作系统上对64位的数据的读写要分两步完成，每一步取32位数据。
	// 这样对double和long的赋值操作就会有问题：如果有两个线程同时写一个变量内存，一个进程写低32位，而另一个写高32位，这样将导致获取的64位数据是失效的数据。
	
	// 注：这里如果加上 volatile 则可以解决32位JVM long操作的原子性问题
	// volatile本身不保证获取和设置操作的原子性，仅仅保持修改的可见性。但是java的内存模型保证声明为volatile的long和double变量的get和set操作是原子的。
	private static long staticLong = 0;
	
	public static void main(String[] args) {
		Thread t1 = new Thread(new UnatomicLong(-1));
		Thread t2 = new Thread(new UnatomicLong(0));
		
		System.out.println(Long.toBinaryString(-1));
		System.out.println(pad(Long.toBinaryString(0), 64));
		
		t1.start();
		t2.start();
		
		long val;
		while ((val = staticLong) == -1 || val == 0) {
			// 如果变量staticLong的值是-1或0，说明两个线程操作没有交叉
		}
		
		System.out.println(pad(Long.toBinaryString(val), 64));
		System.out.println(val);
		
		t1.interrupt();
		t2.interrupt();
	}
	
	
	// 将二进制打印成固定长度，位数不足前补0
	private static String pad(String s, int targetLength) {
		int n = targetLength - s.length();
		for (int x = 0; x < n; x++) {
			s = "0" + s;
		}
		return s;
	}
	
	private static class UnatomicLong implements Runnable {
		
		private final long lv;
		
		UnatomicLong(long lv) {
			this.lv = lv;
		}
		
		public void run() {
			while (!Thread.interrupted()) {
				// 每个线程都试图将自己的私有变量val赋值给类私有静态变量staticLong
				staticLong = lv;
			}
		}
	}
	
}
