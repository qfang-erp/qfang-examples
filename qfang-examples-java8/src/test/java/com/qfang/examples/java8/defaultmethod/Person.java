package com.qfang.examples.java8.defaultmethod;


/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年7月16日
 * @since 1.0
 */
public interface Person {
	
	String NAME_SPACE = "com.jaf.examples.jdk8.defaultmethod.Person";
	
	long getId();
	
	// 默认是 public
	default String getName() {
		return "Person";
	}
	
	public static String getNameSpace() {
		return NAME_SPACE;
	}
	
}
