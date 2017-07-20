package com.qfang.examples.concurrent.part4;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @since 1.0
 */
public class AtomicStampedReferenceTest {
	
	/**
	 * 解决 {@link AtomicReference} ABA 的问题
	 * 
	 * 主要接口
	 * 
	 * {@link AtomicStampedReference#compareAndSet(Object, Object, int, int)}  // 比较设置，参数依次为：期望值 写入新值 期望时间戳 新时间戳 
	 * {@link AtomicStampedReference#getReference()}  // 获得当前对象引用
	 * {@link AtomicStampedReference#getStamp()}    // 获得当前时间戳
 	 * 
	 */
	
	private static AtomicStampedReference<Integer> money = new AtomicStampedReference<Integer>(19, 0);
	

	public static void main(String[] args) {

		// 模拟3个充值线程，不断地充值（小于20元就充值20元），但是只有一个线程能够充值成功
		for(int i = 0; i < 3; i++) {
			new Thread(new Runnable() {
				final int timestamp = money.getStamp();
				
				public void run() {
					while (true) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
						Integer m = money.getReference();
						int nowMoney = m + 20;
						if(m < 20 && money.compareAndSet(m, nowMoney, timestamp, timestamp + 1)) {
							System.out.println("余额小于20元，自动充值成功，现有余额：" + nowMoney);
						}
					}
				}
			}, "producer-thread-" + i).start();
		}
		
		// 模拟5个线程，不断地消费，大于20元就消费10元
		for(int i = 0; i < 5; i++) {
			new Thread(new Runnable() {
				
				public void run() {
					while (true) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
						final int timestamp = money.getStamp();
						Integer m = money.getReference();
						int nowMoney = m -10;
						if(m > 20) {
							if(money.compareAndSet(m, nowMoney, timestamp, timestamp + 1)) {
								System.out.println("余额大于20元，成功消费10元，现有余额：" + nowMoney);
							}
						} else {
							System.out.println("余额不足...");
						}
					}
				}
			}, "consumer-thread-" + i).start();
			
		}
		
	}
	
}
