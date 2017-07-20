package com.qfang.examples.concurrent.part4;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @since 1.0
 */
public class AtomicReferenceTest {
	
	/**
	 * AtomicReference 对任意对象的引用进行多线程下安全更新 {@link AtomicReference#compareAndSet(Object, Object)} 
	 * (更新前的值, 期望更新的值)
	 */
	public static void main(String[] args) throws InterruptedException {
		final Person p100 = new Person(100, "zhangsan");
		final AtomicReference<Person> atomicRef = new AtomicReference<Person>(p100);
		
		// 100个线程对该来同时更新该Person对象，但只会有随机的一个线程能够更新成功，其他的都会失败
		final Random random = new Random();
		final CountDownLatch latch = new CountDownLatch(100);
		for (int i = 0; i < 100; i++) {
			new Thread(new Runnable() {
				public void run() {
					try {
						Thread.sleep(random.nextInt(1000));
						
						String threadName = Thread.currentThread().getName();
						if (atomicRef.compareAndSet(p100, new Person(Integer.parseInt(threadName), "person"))) {
							System.out.println(Thread.currentThread().getName() + " change value success");
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					} finally {
						latch.countDown();
					}
				}
			}, i+"").start();
		}
		
		latch.await();
		Person p = atomicRef.get();
		System.out.println(p.getId() + " : " + p.getName());
	}
	
	private static class Person {
		
		private final int id;
		private final String name;
		
		Person(int id, String name) {
			this.id = id;
			this.name = name;
		}
		
		public int getId() {
			return id;
		}
		
		public String getName() {
			return name;
		}
	}
	
}
