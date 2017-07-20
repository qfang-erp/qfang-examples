package com.qfang.examples.concurrent.part3;


/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @since 1.0
 */
public class OrderExample {
	
	int a = 0;
	boolean flag = false;
	
	public void write() {
		a = 1;
		flag = true;
	}

	public void read() {
		if(flag) {
			int i = a + 1;
			System.out.println(i);
			// ...
		}
	}
	
}
