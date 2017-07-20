package com.qfang.examples.concurrent.part2;

import java.util.Arrays;


/**
 * 
 * 示例：
 * 使用 wait notify 实现一个队列，队列有2个方法，add 和 get 。
 * add方法往队列中添加元素，get方法往队列中获得元素。队列必须是线程安全的。
 * 如果get执行时，队列为空，线程必须阻塞等待，直到有队列有数据。
 * 如果add时，队列已经满，则add线程要等待，直到队列有空闲空间。
 * 
 * @author liaozhicheng.cn@163.com
 * @since 1.0
 */
public class SynchronizedQueue<E> implements SimpleQueueDemo<E> {
	
	private Object[] array;
	private int index = 0;
	
	public SynchronizedQueue(int size) {
		array = new Object[size];
	}
	
	@Override
	public synchronized void put(E item) {
		while(isFull()) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		// 在数组的最末尾添加元素
		array[index++] = item;
		
		StringBuilder log = new StringBuilder();
		log.append(Thread.currentThread().getName()).append(this);
		System.out.println(log.toString());
		
		this.notifyAll();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public synchronized E take() {
		while(isEmpty()) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		// 将数组最头上的元素弹出（最早进入的 FIFO）
		E item = (E) array[0];
		// 将原数组中第二个元素到最后一个元素copy到新数组
		array = Arrays.copyOfRange(array, 1, array.length + 1);
		array[array.length - 1] = null;
		index--;
		
		StringBuilder log = new StringBuilder();
		log.append(Thread.currentThread().getName()).append(this);
		System.out.println(log.toString());
		
		this.notifyAll();
		return item;
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
