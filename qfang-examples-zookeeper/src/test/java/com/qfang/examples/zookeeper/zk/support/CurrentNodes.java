package com.qfang.examples.zookeeper.zk.support;

import java.util.Arrays;
import java.util.Optional;

/**
 * 因为 zk 的临时节点下不能有子节点，所以需要创建永久节点进行测试
 * 创建的永久节点需要在会话结束时手动删除，否则下次重新创建节点时测试代码会有问题
 * 该工具类用来记录所有创建的节点名称，并且在会话关闭之前清除所有创建的节点
 *
 * @author liaozhicheng.cn@163.com
 */
public class CurrentNodes {

    private static ThreadLocal<MyStack<String>> CURRENT_NODES = new ThreadLocal<>();

    public static void init() {
        CURRENT_NODES.set(new MyStack<>());
    }

    public static void put(String path) {
        CURRENT_NODES.get().put(path);
    }


    public static Optional<String> pop() {
        return CURRENT_NODES.get().pop();
    }

    public static boolean isEmpty() {
        return CURRENT_NODES.get().isEmpty();
    }

    private static class MyStack<T> {

        private final int DEFUALT_SIZE = 10;
        private int index = 0;

        private T[] datas;

        @SuppressWarnings("unchecked")
		private MyStack() {
            this.datas = (T[]) new Object[DEFUALT_SIZE];
        }

        public void put(T obj) {
            if(isFull()) {
                T[] newDatas = Arrays.copyOf(datas, datas.length + DEFUALT_SIZE);
                datas = newDatas;
            }
            datas[index++] = obj;
        }

        public Optional<T> pop() {
            if(isEmpty())
                return Optional.empty();

            int i = index - 1;
            T value = datas[i];
            datas[i] = null;
            index = i;
            return Optional.of(value);
        }

        public boolean isEmpty() {
            return index <= 0;
        }

        public boolean isFull() {
            return index >= datas.length;
        }

    }

}
