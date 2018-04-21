package com.qfang.examples.concurrent.pc;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author: liaozhicheng.cn@163.com
 * @date: 2018-04-07
 * @since: 1.0
 */
public class TestMain {

    private static ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();

    public static void main(String[] args) {
        List<Thread> producerList = IntStream.range(0, 10)
                .mapToObj(i -> new Thread(new Producer(), "producer thread:" + i))
                .collect(Collectors.toList());
        producerList.forEach(pt -> pt.start());

        // Acceptor 只负责接收任务和创建任务，但是任务的处理放到 Consumer 线程池中异步处理
        Consumer consumer = new Consumer();
        Acceptor acceptor = new Acceptor(consumer);
        new Thread(acceptor).start();
    }

    static class Producer implements Runnable {

        @Override
        public void run() {
            IntStream.range(0, 100000).forEach(i -> queue.add(Thread.currentThread().getName() + " - " + i));
        }
    }

    static class Consumer {

        static ExecutorService executorService = Executors.newFixedThreadPool(20);

        void accept(String message) {
            executorService.execute(new ConsumerTask(message));
        }

    }

    static class ConsumerTask implements Runnable {

        private final String message;

        ConsumerTask(String message) {
            this.message = message;
        }

        @Override
        public void run() {
            System.out.println(message);
        }
    }


    static class Acceptor implements Runnable {

        private final Consumer consumer;

        Acceptor(Consumer consumer) {
            this.consumer = consumer;
        }

        @Override
        public void run() {
            for(;;) {
                String message = queue.poll();
                if(message != null)
                    consumer.accept(message);
            }
        }
    }

}
