package com.qfang.examples.concurrent.part4;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

import com.qfang.examples.concurrent.common.ThreadUtils;


/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @since 1.0
 */
public class AtomicReferenceFieldUpdaterTest {
	
	private static final int SIZE = 100;
	
	public static void main(String[] args) throws InterruptedException {
		final AtomicReferenceFieldUpdater<Person, String> nameFieldUpdater = 
				AtomicReferenceFieldUpdater.newUpdater(Person.class, String.class, "name");
		final AtomicIntegerFieldUpdater<Person> ageFieldUpdater = 
				AtomicIntegerFieldUpdater.newUpdater(Person.class, "age");
		
		final Person person = new Person(1, "zhangsan", 20);
		final Random radom = new Random();
		final CountDownLatch latch = new CountDownLatch(SIZE);
		for(int i = 0; i < SIZE; i++) {
			new Thread(new Runnable() {
				public void run() {
					try {
						TimeUnit.MICROSECONDS.sleep(radom.nextInt(1000));
						if(nameFieldUpdater.compareAndSet(person, "zhangsan", "lisi")) {
							System.out.println(Thread.currentThread().getName() + " update field name success.");
						}
						
						ThreadUtils.sleepSilently(radom.nextInt(1000));
						if(ageFieldUpdater.compareAndSet(person, 20, 30)) {
							System.out.println(Thread.currentThread().getName() + " update field age success.");
						}
					} catch(Exception e) {
						e.printStackTrace();
					} finally {
						latch.countDown();
					}
				}
			}, "thread"+i).start();
		}
		latch.await();
	}
	
	private static class Person {
		int id;
		volatile String name;
		volatile int age;
		
		Person(int id, String name, Integer age) {
			this.id = id;
			this.name = name;
			this.age = age;
		}

		@Override
		public String toString() {
			return id + ", " + name + ", " + age;
		}
		
	}
	
}
