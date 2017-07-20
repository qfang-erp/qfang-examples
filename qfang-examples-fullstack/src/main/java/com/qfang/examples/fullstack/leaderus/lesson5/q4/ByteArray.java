package com.qfang.examples.fullstack.leaderus.lesson5.q4;

import java.io.Serializable;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2016年9月9日
 * @since 1.0
 */
public interface ByteArray extends Serializable {
	
	void append(byte b);
	
	byte poll();
	
	byte getAndUpdate(int index, byte update);
	
	int getCurPos();
	
	byte[] getArray();
	
}
