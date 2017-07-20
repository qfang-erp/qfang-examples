package com.qfang.examples.concurrent.part5;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @since 1.0
 */
public class ForkJoinTest {
	

	// 计算给定函数 y=1/x 在定义域 [1,100]上y轴与x轴围成的面积，计算步长0.01
	public static void main(String[] args) {
		ForkJoinPool forkJoinPool = new ForkJoinPool();
		CalculateTask ct = new CalculateTask(1.00d, 100.00d, 0.01d);
		ForkJoinTask<Double> result = forkJoinPool.submit(ct);
		try {
			double sum = result.get();
			System.out.println(sum);
			forkJoinPool.shutdown();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	static class CalculateTask extends RecursiveTask<Double> {
		
		private static final long serialVersionUID = 2998367083493839209L;
		
		// [1, 100] 取值，步长0.01 则是 9900 次计算，分成10个线程来计算
		
		private static final int TOTAL_THREAD_COUNT = 10;
		
		private static final double PRE_THREAD_RANGE = 10d;
		
		private final double start;
		
		private final double end;
		
		private final double step;
		
		
		CalculateTask(double start, double end, double step) {
			this.start = start;
			this.end = end;
			this.step = step;
		}


		@Override
		protected Double compute() {
			double sum = 0.0d;
			if (end - start <= PRE_THREAD_RANGE) {
				// 计算
				for (double x = start, posX = start; (posX = posX + step) < end; x = x + step) {
					double y = 1 / x;
					sum += (step * y);
				}
			} else {
				// 切分任务
				List<CalculateTask> subTasks = new ArrayList<CalculateTask>(TOTAL_THREAD_COUNT);
				
				DecimalFormat df = new DecimalFormat("###0.00");
				double subStart = start;
				for (int i = 0; i < TOTAL_THREAD_COUNT; i++) {
					double subEnd = subStart + PRE_THREAD_RANGE;
					subEnd = subEnd > end ? end : subEnd;
					CalculateTask subTask = new CalculateTask(Double.valueOf(df.format(subStart)),
							Double.valueOf(df.format(subEnd)), this.step);
					subTasks.add(subTask);
					subStart = subEnd;
					subTask.fork();
				}
				
				for (CalculateTask st : subTasks) {
					sum += st.join();
					System.out.println(st);
				}
			}
			return sum;
		}
		
		
		@Override
		public String toString() {
			return "[" + start + ", " + end + "]";
		}
		
	}

}
