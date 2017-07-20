package com.qfang.examples.fullstack.leaderus.lesson5.q4;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2016年9月9日
 * @since 1.0
 */
public class SynchronizeByteArray implements ByteArray {

	private static final long serialVersionUID = 2469300358246353543L;

	private volatile int curPos = -1;  // -1, 表示数组为空
	private final byte[] array;
	
	public SynchronizeByteArray(int length) {
		this.array = new byte[length];
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
	public synchronized void append(byte b) {
		int nextIdx = curPos + 1;
		if (nextIdx < 0 || nextIdx >= array.length)
//            throw new IndexOutOfBoundsException("index " + curPos);
			return;
		
		array[nextIdx] = b;
		curPos = nextIdx;
	}

	@Override
	public synchronized byte poll() {
		if(curPos <= -1)
//			throw new IndexOutOfBoundsException("index " + curPos);
			return 0;
		
		byte b = array[curPos];
		array[curPos] = 0;
		curPos--;
		return b;
	}

	@Override
	public byte getAndUpdate(int index, byte update) {
		
		
		return 0;
	}

}
