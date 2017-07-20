package com.qfang.examples.fullstack.leaderus.lesson6;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2016年9月14日
 * @since 1.0
 */
public class TestOnlyLock {
	
	private static ReentrantLock lock = new ReentrantLock();
	
	private static ArrayList<String> datas = new ArrayList<String>();
	
	public static void main(String[] args) {
		List<Thread> threads = IntStream.range(1, 10).mapToObj(i -> {
			if (i % 2 == 0) {
				return new MThread("consumer " + i);
			} else
				return new NThread("producer " + i);
		}).filter(t -> {
			t.start();
			return true;
		}).collect(toList());

		threads.forEach(t -> {
			try {
				t.join();
			} catch (InterruptedException e) {
				// ignore
			}
		});
	}

	static boolean isFull() {
		return datas.size() >= 1;
	}

	static boolean isEmpty() {
		return datas.isEmpty();
	}
	
	private static class MThread extends Thread {

		public MThread(String string) {
			this.setName(string);
		}

		public void run() {
			while (true) {
				if(TestOnlyLock.isEmpty())
					continue ;
				
				// 4 2 6
				lock.lock();
				System.out.println(Thread.currentThread().getName() + " get");
				try {
					if(TestOnlyLock.isEmpty()) {
//						lock.unlock();
						continue ;
					}
					
					System.out.println(Thread.currentThread().getName() + " begin get ====");
					
//					if (TestOnlyLock.isEmpty()) {
//						System.out.println("impossible empty !! " + Thread.currentThread().getName());
//						System.exit(-1);
//					}
					
					TestOnlyLock.datas.forEach(s -> System.out.println(s));
					TestOnlyLock.datas.clear();
				} finally {
					System.out.println("empty ...");
					lock.unlock();
				}
			}
			
		}

	}

	private static class NThread extends Thread {

		public NThread(String string) {
			this.setName(string);
		}

		public void run() {
			while (true) {
				if(TestOnlyLock.isFull())
					continue ;
				
				lock.lock();
				System.out.println(Thread.currentThread().getName() + " set");
				try {
					if(TestOnlyLock.isFull()) {
//						lock.unlock();
						continue ;
					}
					
					System.out.println(Thread.currentThread().getName() + " begin add");

//					if (TestOnlyLock.datas.size() >= 1) {
//						System.out.println("impossible full !! " + Thread.currentThread().getName());
//						System.exit(-1);
//					}

					IntStream.range(0, 1).forEach(i -> TestOnlyLock.datas.add(i + " data"));
				} finally {
					System.out.println("set empty ...");
					lock.unlock();
				}
			}
		}

	}
	
}
