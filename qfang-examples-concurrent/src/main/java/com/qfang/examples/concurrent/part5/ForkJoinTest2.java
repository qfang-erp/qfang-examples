package com.qfang.examples.concurrent.part5;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

/**
 * @author: liaozhicheng.cn@163.com
 * @date: 2018-04-07
 * @since: 1.0
 */
public class ForkJoinTest2 {

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        CalculatorTask task = new CalculatorTask(1, 1000);
        ForkJoinTask<Long> result = forkJoinPool.submit(task);
        try {
            long sum = result.get();
            System.out.println(sum);
            forkJoinPool.shutdown();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    static class CalculatorTask extends RecursiveTask<Long> {

        private static final long serialVersionUID = 5900916773138252907L;
        private final long STEP = 100;

        private final long start;
        private final long end;

        CalculatorTask(long start, long end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            long sum = 0;
            if(this.end - this.start > STEP) {
                // 拆分任务
                List<CalculatorTask> subTasks = this.splitTask(this);
                for(CalculatorTask subTask : subTasks) {
                    long subSum = subTask.join();
                    System.out.println("task : " + subTask + ", subSum: " + subSum);
                    sum += subSum;
                }
            } else {
                // 计算和
                sum += LongStream.range(this.start, this.end).map(i -> i*i*i).sum();
            }
            return sum;
        }

        /**
         * 拆分任务，每个任务只计算 100 个数内的立方和
         * @param bigTask
         * @return
         */
        private List<CalculatorTask> splitTask(CalculatorTask bigTask) {
            long start = bigTask.start, end = bigTask.end, currentStart = start;
            List<CalculatorTask> subTasks = new ArrayList<>();
            while(currentStart <= end) {
                long currentEnd = currentStart + STEP;
                CalculatorTask subTask = new CalculatorTask(currentStart, currentEnd > end ? end : currentEnd);
                subTask.fork();
                subTasks.add(subTask);

                currentStart = currentEnd;
            }
            return subTasks;
        }

        @Override
        public String toString() {
            return "CalculatorTask{" +
                    "start=" + start +
                    ", end=" + end +
                    '}';
        }
    }


}
