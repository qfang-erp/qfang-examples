package com.qfang.examples.fullstack.leaderus.lesson5.q4;


/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年9月10日
 * @since 1.0
 */
public class UnsafeBasedByteArray implements ByteArray {

	private static final long serialVersionUID = -4970500417807207278L;

	private int curPos;
	private byte[] array;
	
	public UnsafeBasedByteArray(int length) {
		this.array = new byte[length];
	}
	
	@Override
	public void append(byte b) {
		int cp = curPos;
		// LoadLoad + LoadStore 栅栏，确保读取到最新的 curPos
		UnsafeUtils.getUnsafe().loadFence();
		
		array[cp] = b;
		curPos = cp + 1;
		// StoreStore + LoadStore 栅栏，确保写入值能够立刻被其他读线程看到
		UnsafeUtils.getUnsafe().storeFence();
	}

	@Override
	public byte poll() {
		int cp = curPos;
		byte b = array[cp];
		UnsafeUtils.getUnsafe().loadFence();
		
		array[cp] = 0;
		curPos = cp - 1;
		UnsafeUtils.getUnsafe().storeFence();
		return b;
	}

	@Override
	public byte getAndUpdate(int index, byte update) {
		int cp = curPos;
		byte[] bs = array;
		UnsafeUtils.getUnsafe().loadFence();
		
		if(index > cp)
			throw new IllegalArgumentException("getAndUpdate");
		
		byte old = bs[index];
		array[index] = update;
		UnsafeUtils.getUnsafe().storeFence();
		return old;
	}

	@Override
	public int getCurPos() {
		// 相当于一个 volatile 的读取 curPos，确保读线程每次都读到最新值
		int cp = curPos;
		UnsafeUtils.getUnsafe().loadFence();
		return cp;
	}

	@Override
	public byte[] getArray() {
		byte[] ba = array;
		UnsafeUtils.getUnsafe().loadFence();
		return ba;
	}
	
}
