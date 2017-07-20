package com.qfang.examples.concurrent.part2;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 基于 ReentrantLock 和 Condition 实现队列
 * 
 * @author liaozhicheng.cn@163.com
 * @since 1.0
 */
public class ConditionQueue<E> implements SimpleQueueDemo<E> {

	private Object[] array;
	private int index = 0;
	
	private ReentrantLock lock = new ReentrantLock();
	
	private Condition notEmpty = lock.newCondition();
	private Condition notFull = lock.newCondition();
	
	
	public ConditionQueue(int size) {
		this.array = new Object[size];
	}
	
	@Override
	public void put(E item) {
		lock.lock();
		try {
			while(isFull()) {
				notFull.await();
			}
			
			// 在数组的最末尾添加元素
			array[index++] = item;
			
			StringBuilder log = new StringBuilder();
			log.append(Thread.currentThread().getName()).append(this);
			System.out.println(log.toString());
			
			notEmpty.signal();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public E take() {
		lock.lock();
		try {
			while(isEmpty()) {
				notEmpty.await();
			}
			
			E item = (E) array[0];
			// 将原数组中第二个元素到最后一个元素copy到新数组
			array = Arrays.copyOfRange(array, 1, array.length + 1);
			array[array.length - 1] = null;
			index--;
			
			StringBuilder log = new StringBuilder();
			log.append(Thread.currentThread().getName()).append(this);
			System.out.println(log.toString());
			
			notFull.signal();
			return item;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		return null;
	}
	
	private boolean isFull() {
		return index >= array.length;
	}
	
	private boolean isEmpty() {
		return index <= 0;
	}

	@Override
	public String toString() {
		return Arrays.toString(array);
	}
	
}
