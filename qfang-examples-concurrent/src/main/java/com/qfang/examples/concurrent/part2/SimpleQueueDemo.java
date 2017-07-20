package com.qfang.examples.concurrent.part2;


/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @since 1.0
 */
public interface SimpleQueueDemo<E> {
	
	void put(E e);
	
	E take();

}
