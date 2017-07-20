package com.qfang.examples.pattern.singleton;


/**
 * 既实现了延迟初始化，又能保证单例
 * 推荐使用这种方式
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年9月16日
 * @since 1.0
 */
public class Singleton4 {
	
	private static class SingletonHolder {
		private static final Singleton4 INSTANCE = new Singleton4();
	}
	
	private Singleton4() {}
	
	public static Singleton4 getInstance() {
		return SingletonHolder.INSTANCE;
	}
	
}
