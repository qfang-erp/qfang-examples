package com.qfang.examples.concurrent.part8;

import java.util.concurrent.locks.StampedLock;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @since 1.0
 */
public class StampedLockTest {

	
	static class Point {
		private double x, y;
		private static final StampedLock lock = new StampedLock();
		
		void move(double deltaX, double deltaY) {
			long stamp = lock.writeLock();
			try {
				x += deltaX;
				y += deltaY;
			} finally {
				lock.unlockWrite(stamp);
			}
		}
		
		double distanceFromOrigin() {
			long stamp = lock.tryOptimisticRead(); // 乐观读
			double currentX = x, currentY = y;
			if(!lock.validate(stamp)) {  // 校验读取之后是否被修改过
				stamp = lock.readLock();  // 如果被修改过，升级为正常读锁
				try {
					currentX = x;
					currentY = y;
				} finally {
					lock.unlockRead(stamp);
				}
			}
			return Math.sqrt(currentX * currentX + currentY * currentY);
		}
		
	}
	
}
