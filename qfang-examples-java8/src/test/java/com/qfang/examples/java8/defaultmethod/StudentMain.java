package com.qfang.examples.java8.defaultmethod;


/**
 * 1、一个类实现两个接口，而且两个接口都提供了相同的默认方法实现，会出现什么问题
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年7月16日
 * @since 1.0
 */
public class StudentMain implements Person, Named {

	@Override
	public long getId() {
		return 0;
	}

	/**
	 * 如果 {@link StudentMain } 只实现一个 {@link Person} 接口，则可以不覆盖 {@link #getName()} 方法
	 * 而采用接口中默认的 {@link Person#getName()} 默认实现
	 * 
	 * 但是由于该类实现两个接口，两个接口中都有提供 {@link #getName()} 方法的默认实现
	 * 所以这里需要显示地覆盖接口默认方法的实现，并且明确指出调用的是 {@link Person} 接口的默认实现
	 */
	@Override
	public String getName() {
		return Person.super.getName();
	}
	
}
