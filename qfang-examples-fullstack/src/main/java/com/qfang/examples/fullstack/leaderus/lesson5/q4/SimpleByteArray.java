package com.qfang.examples.fullstack.leaderus.lesson5.q4;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2016年9月9日
 * @since 1.0
 */
public class SimpleByteArray implements ByteArray {

	private static final long serialVersionUID = -7535477948290106884L;

	private final byte[] array;
	private volatile int curPos;
	
	public SimpleByteArray(int length) {
		array = new byte[length];
		curPos = -1;
	}
	
	@Override
	public int getCurPos() {
		return curPos;
	}

	@Override
	public byte[] getArray() {
		return array;
	}

	@Override
	public void append(byte b) {
		int nextIndex = curPos + 1;
		array[nextIndex] = b;
		curPos = nextIndex;
	}

	@Override
	public byte poll() {
		int prevIndex = curPos - 1;
		byte b = array[curPos];
		array[curPos] = 0;
		curPos = prevIndex;
		return b;
	}

	@Override
	public byte getAndUpdate(int index, byte update) {
		if(index > curPos)
			throw new IllegalArgumentException("getAndUpdate");
		
		byte[] bs = array;
		byte old = bs[index];
		UnsafeUtils.getUnsafe().loadFence();
		
		array[index] = update;
		UnsafeUtils.getUnsafe().storeFence();
		return old;
	}

}
