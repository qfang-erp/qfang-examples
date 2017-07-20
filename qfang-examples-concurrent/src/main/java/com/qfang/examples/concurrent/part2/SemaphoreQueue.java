package com.qfang.examples.concurrent.part2;

import java.util.Arrays;
import java.util.concurrent.Semaphore;

/**
 * 基于 Semaphore 来实现一个阻塞队列
 * 
 * @author liaozhicheng
 * @date 2016年9月18日
 * @since 1.0
 */
public class SemaphoreQueue<E> implements SimpleQueueDemo<E> {

	private Object[] array;
	private int index = 0;
	
	private Semaphore takeSemaphore;  // 可以获取的元素个数
	private Semaphore spaceSemaphore;  // 未空的元素个数（takeSemaphore + spaceSemaphore 这两个许可总数相加总是等于数组的长度）
	
	public SemaphoreQueue(int size) {
		this.array = new Object[size];
		this.takeSemaphore = new Semaphore(0);  // 初始值为0，因为初始化时数组中没有元素
		this.spaceSemaphore = new Semaphore(size);
	}
	
	@Override
	public void put(E e) {
		try {
			spaceSemaphore.acquire();  // 如果数组满了，这里将阻塞等待
			this.putInner(e);
			takeSemaphore.release();
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	public synchronized void putInner(E e) {
		array[index++] = e;
		
		StringBuilder log = new StringBuilder();
		log.append(Thread.currentThread().getName()).append(this);
		System.out.println(log.toString());
	}
	
	@Override
	public E take() {
		E obj = null;
		try {
			takeSemaphore.acquire();  // 如果数组为空，这里将阻塞等待，直到有元素成功添加进来
			obj = this.takeInner();
			spaceSemaphore.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	@SuppressWarnings("unchecked")
	private synchronized E takeInner() {
		int i = index - 1;
		E obj = (E) array[i];  // 从数组的末尾弹出元素
		array[i] = null;
		index = i;
		
		StringBuilder log = new StringBuilder();
		log.append(Thread.currentThread().getName()).append(this);
		System.out.println(log.toString());
		
		return obj;
	}
	
	@Override
	public String toString() {
		return Arrays.toString(array);
	}

}
