package com.qfang.examples.j2se.java.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * @author: liaozhicheng
 * @Timestamp: 2017/02/10 11:46
 */
public class ExecutorTest {

    private static ThreadLocal<Integer> valHolder = new ThreadLocal<>();

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        IntStream.range(0, 100).forEach(i -> {
            executorService.execute(() -> {
                TestRunnable tr = new TestRunnable(i);
                tr.run();
                System.out.println(valHolder.get());
            });
        });
        executorService.shutdown();
    }

    private static class TestRunnable implements Runnable {

        private final int val;

        private TestRunnable(int val) {
            this.val = val;
        }

        @Override
        public void run() {
            valHolder.set(this.val);
        }
    }


}
