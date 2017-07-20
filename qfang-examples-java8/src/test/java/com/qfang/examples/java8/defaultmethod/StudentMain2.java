package com.qfang.examples.java8.defaultmethod;


/**
 * 1、抽象类和接口都提供了相同方法的默认实现，到底哪一个会生效（类优选原则）
 * {@link AbstractPerson#getName()}
 * {@link Named#getName()}
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年7月17日
 * @since 1.0
 */
public class StudentMain2 extends AbstractPerson implements Named {
	
	public static void main(String[] args) {
		StudentMain2 student = new StudentMain2();
		// 基于类优先原则，这里调用的是抽象类里面的实现，而接口中的默认实现会被覆盖
		System.out.println(student.getName());  // abstract person
	}
	
}
