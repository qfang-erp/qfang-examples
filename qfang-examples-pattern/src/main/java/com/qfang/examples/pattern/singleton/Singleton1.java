package com.qfang.examples.pattern.singleton;


/**
 * 采用静态变量直接初始化，简单有效，在该类第一次被虚拟机加载的时候回实例化变量实例，不适用大对象
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年9月16日
 * @since 1.0
 */
public class Singleton1 {
	
	private static final Singleton1 INSTANCE = new Singleton1();
	
	private Singleton1() {
		// 私有化的构造方法
	}
	
	public static Singleton1 getInstance() {
		return INSTANCE;
	}
	
}
