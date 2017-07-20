package com.qfang.examples.concurrent.part6.mock;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @since 1.0
 */
public class Client {
	
	public static void main(String[] args) {
		Data<String> future = request("hello", " world!");
		System.out.println("do other things...");
		System.out.println(future.get());
	}

	public static Data<String> request(final String str1, final String str2) {
		final FutureData<String> future = new FutureData<String>();
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				RealData<String> realData = new RealData<String>(str1, str2);
				future.setRealData(realData);
			}
		});
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return future;
	}
	
}
