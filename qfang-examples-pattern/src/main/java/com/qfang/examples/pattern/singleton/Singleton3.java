package com.qfang.examples.pattern.singleton;

import com.qfang.examples.pattern.common.NotRecommend;

/**
 * 双重检查加锁（DCL），减少加锁的次数，并且实现了延迟初始化
 * DCL已经不被推荐使用
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年9月16日
 * @since 1.0
 */
@NotRecommend
public class Singleton3 {
	
	// 由于指令重排及可见性问题，这里的 volatile 是必须的
	private static volatile Singleton3 instance;
	
	private Singleton3() {
	}
	
	public static Singleton3 getInstance() {
		if(instance == null) {
			synchronized (Singleton3.class) {
				if(instance == null) {
					instance = new Singleton3();
				}
			}
		}
		return instance;
	}
	
}
