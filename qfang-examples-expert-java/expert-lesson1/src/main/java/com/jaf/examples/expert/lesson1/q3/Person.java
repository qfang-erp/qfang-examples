package com.jaf.examples.expert.lesson1.q3;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2016年8月11日
 * @since 1.0
 */
class Person {

	final int id;
	final String name;
	final int age;

	public Person(int id, String name, int age) {
		this.id = id;
		this.name = name;
		this.age = age;
	}
	
	@Override
	public String toString() {
		return id + "," + name + "," + age;
	}

}
