package com.qfang.examples.pattern.singleton;

import com.qfang.examples.pattern.common.NotRecommend;

/**
 * 延迟实例化，直接在方法上同步，弊端是每次获取实例对象时都需要加锁
 * 不推荐使用，在高并发的情况下很影响性能
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年9月16日
 * @since 1.0
 */
@NotRecommend
public class Singleton2 {
	
private static Singleton2 INSTANCE;
	
	private Singleton2() {
		// 私有化的构造方法
	}
	
	public synchronized Singleton2 getInstance() {
		if(INSTANCE == null) 
			INSTANCE = new Singleton2();
		
		return INSTANCE;
	}
	
}
