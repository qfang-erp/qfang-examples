package com.qfang.examples.concurrent.common;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @since 1.0
 */
@NotThreadSafe
public class UnsafeCachingProduct {

	private final AtomicInteger lastNumber = new AtomicInteger();
	private final AtomicLong lastProduct = new AtomicLong();
	
	public synchronized long doProduct(int i) {
		if(lastNumber.get() == i) {
			return lastProduct.get();
		}
		else {
			long productResult = (long) i * 2L;
			lastNumber.set(i);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			lastProduct.set(productResult);
			return productResult;
		}
	}
	
	
	static class ProductTestThread implements Runnable {

		private final UnsafeCachingProduct product;
		
		ProductTestThread(UnsafeCachingProduct product) {
			this.product = product;
		}
		
		public void run() {
			Random r = new Random();
			int i = r.nextInt(10);
			long pr = product.doProduct(i);
			if(pr != i * 2) {
				System.out.println(i + " : " + pr);
			}
		}
	}

	public static void main(String[] args) {
		UnsafeCachingProduct ucp = new UnsafeCachingProduct();
		
		for(int i = 0; i < 200; i++) {
			new Thread(new ProductTestThread(ucp), "thread-" + i).start();
		}
		
	}
	
}
