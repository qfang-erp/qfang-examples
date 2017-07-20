package com.qfang.examples.fullstack.leaderus.lesson5.q4;

import java.util.Arrays;

import sun.misc.Unsafe;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年9月8日
 * @since 1.0
 */
public class AtomicByteArray implements ByteArray {
	
	private static final long serialVersionUID = -3761514037304904162L;
	
	private static final Unsafe unsafe = UnsafeUtils.getUnsafe();
	private static final int BASE;  // 保存 byte[] 第一个元素的偏移量
	private static final int shift;
	private static final long CURPOS_OFFSET;
	private static final long ARRAYBUSY_OFFSET;
	
	private volatile int curPos;
	private final byte[] array;
	private volatile int arrayBusy;
	
	static {
		try {
			BASE = unsafe.arrayBaseOffset(byte[].class);
			int scale = unsafe.arrayIndexScale(byte[].class);
	        if ((scale & (scale - 1)) != 0)
	            throw new Error("data type scale not a power of two");
	        shift = 31 - Integer.numberOfLeadingZeros(scale);
			
	        Class<?> abac = AtomicByteArray.class;
	        CURPOS_OFFSET = unsafe.objectFieldOffset(abac.getDeclaredField("curPos"));
	        ARRAYBUSY_OFFSET = unsafe.objectFieldOffset(abac.getDeclaredField("arrayBusy"));
		} catch (Exception e) {
			throw new Error(e);
		}
	}
	
    private long itemOffset(int index) {
    	return BASE + ((long) index << shift);
    	// return base + i;  // 因为byte占1位，第i个元素，往后移i位即可
    }
    
    private boolean casArrayBusy() {
        return unsafe.compareAndSwapInt(this, ARRAYBUSY_OFFSET, 0, 1);
    }
    
    private boolean casCurPos(int o, int n) {
    	return unsafe.compareAndSwapInt(this, CURPOS_OFFSET, o, n);
    }

    public AtomicByteArray(int length) {
		this.array = new byte[length];
	}
    
    @Override
    public int getCurPos() {
    	return this.curPos;
    }
    
    @Override
    public byte[] getArray() {
    	return this.array;
    }
    
	@Override
	public void append(byte b) {
		for(;;) {
			int cp = curPos, al = array.length;
			if(cp < 0 || cp >= al) {
				continue ;
			}
			
			if(arrayBusy == 0 && casArrayBusy()) {  // 使用 Volatile + CAS 模拟锁的效果
				cp = curPos;  // 重新读取，并且校验
				if(cp < 0 || cp >= al) {
					arrayBusy = 0;
					continue ;
				}
				
				try {
					unsafe.putByteVolatile(array, itemOffset(cp), b);
					casCurPos(cp, cp + 1);
					
					System.out.format("append success, curPos : %s, value : %s, array : %s \n", 
							cp + 1, b, Arrays.toString(array));
					return ;
				} finally {
					arrayBusy = 0;
				}
			}
		}
	}
	
	@Override
	public byte poll() {
		for(;;) {
			int cp = curPos;
			if(cp <= 0) {
				continue ;
			}
			
			if(arrayBusy == 0 && casArrayBusy()) {
				cp = curPos;  // recheck
				if(cp <= 0) {
					arrayBusy = 0;
					continue ;
				}
				
				int prev = cp - 1;
				byte oldVal;
				try {
					oldVal = array[prev];
					unsafe.putByteVolatile(array, itemOffset(prev), (byte) 0);
					casCurPos(cp, prev);
					
					System.out.format("poll success, curPos : %s, value : %s, array : %s \n", 
							prev, oldVal, Arrays.toString(array));
				} finally {
					arrayBusy = 0;
				}
				return oldVal;
			}
		}
	}

	@Override
	public byte getAndUpdate(int index, byte update) {
		return 0;
	}
	
}
