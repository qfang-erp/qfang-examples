package com.qfang.examples.zeroc.ice.service;

import com.jaf.examples.demo._MyServiceDisp;

import Ice.Current;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年7月17日
 * @since 1.0
 */
public class MyServiceImpl extends _MyServiceDisp {

	private static final long serialVersionUID = -1379964152132743192L;

	@Override
	public String hello(Current __current) {
		return "hello ice";
	}
	
}
