package com.qfang.examples.pattern.singleton;

import com.qfang.examples.pattern.common.NotRecommend;

/**
 * 没有使用任何的同步来不安全地发布一个对象
 * 
 * 对于这个类最糟糕的情况不是创建了多个实例，而是可能会导致其他线程看到没有构造完成的对象
 * 即可能会导致有些线程拿到的 instance 对象里面的 x,y 都等于0，或者 x=1, y=0；或者 y=2,x=0 都有可能
 * 导致这种情况的原因是因为指令重排
 * 在构造函数中对 x=1, y=2 的赋值指令，以及将 new Singleton5() 对象赋值给 instance 属性的指令可能发生重排序
 * 所以可能会导致线程A在构造对象时，线程B已经看到对象的引用(instance 属性)已经被赋值，但是x,y的赋值指令可能还没有刷回主存，B获取到了一个构造不完全的对象
 * 
 **********************************************************************************************
 * 除了不可变对象外，使用被另一个线程初始化的对象通常都是不安全的，除非对象的发布操作是在使用该对象的线程开始使用之前执行。   *
 **********************************************************************************************
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年9月16日
 * @since 1.0
 */
@NotRecommend
public class Singleton5 {
	
	private static Singleton5 instance;
	
	private int x, y;
	
	private Singleton5() {
		x = 1;
		y = 2;
	}
	
	public static Singleton5 getInstance() {
		if(instance == null)
			instance = new Singleton5();
		
		return instance;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

}
