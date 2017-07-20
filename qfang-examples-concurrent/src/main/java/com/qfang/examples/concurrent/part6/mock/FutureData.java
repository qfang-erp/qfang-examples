package com.qfang.examples.concurrent.part6.mock;


/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @since 1.0
 */
public class FutureData<V> implements Data<V> {
	
	private RealData<V> realData;

	@Override
	public V get() {
		realData.call();
		return realData.get();
	}

	public void setRealData(RealData<V> realData) {
		this.realData = realData;
	}
	
}
