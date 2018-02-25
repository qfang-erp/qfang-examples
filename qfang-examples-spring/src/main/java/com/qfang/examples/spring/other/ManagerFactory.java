package com.qfang.examples.spring.other;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author: liaozhicheng.cn@163.com
 * @date: 2018-02-10
 * @since: 1.0
 */
public class ManagerFactory {

    private static final Map<String, Manager> managerCache = new ConcurrentHashMap<>();

    private static Manager getManageInstance(String userId) {
        Manager manage = managerCache.get(userId);
        if(manage != null)
            return manage;

        // 如果是多线程环境下，这里会创建多个无用对象
        manage = new Manager(userId);
        // putIfAbsent 方法内部实现会加锁，所以可以确保多线程环境下只有一个线程可以 put 成功
        // put 成功的那个线程返回值一定为 null
        if(managerCache.putIfAbsent(userId, manage) == null)
            return manage;

        // put 的值不为 null，说明其他线程已经放入成功了，直接获取其他线程放入的值，保证对于同一个 key 获取到的值是同一个对象引用
        return managerCache.get(userId);
    }

    public static void main(String[] args) {
        final String userId1 = "0001";
        final String userId2 = "0002";

        List<Thread> threads = IntStream.range(0, 10).mapToObj(i -> new Thread(() -> {
            IntStream.range(0, 20).forEach(j -> {
                Manager m = ManagerFactory.getManageInstance(j % 2 == 0 ? userId1 : userId2);
                System.out.println(Thread.currentThread().getName() + ", " +  m);
            });
        }, "thread " + i)).collect(Collectors.toList());

        threads.forEach(Thread::start);
    }


    static class Manager {

        private final String userId;
        private final String sed;

        Manager(String userId) {
            this.userId = userId;
            this.sed = UUID.randomUUID().toString();
            System.out.println("new Manage ....");
        }

        @Override
        public String toString() {
            return "userId: " + userId + ", sed: " + sed;
        }
    }

}


