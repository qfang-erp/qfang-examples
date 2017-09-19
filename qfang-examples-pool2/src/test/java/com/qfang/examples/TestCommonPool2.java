package com.qfang.examples;

import org.apache.commons.pool2.impl.GenericObjectPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author huxianyong
 * @date 2017/8/16
 * @since 1.0
 */
public class TestCommonPool2 {

    public static void main(String[] args) {

        PersonPool pool=new PersonPool();
        ExecutorService executorService= Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*8);
        List<Future> list=new ArrayList<>();
        for(int i=0;i<100 ;i++) {
            Future future= executorService.submit(() -> {
                for(int j=0;j<10000;j++) {
                    Person person = pool.borrowObject();
                    person.work();
                    pool.returnObject(person);
                }
            });
            list.add(future);
        }
        for (Future futrue:list) {
            try {
                futrue.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        pool.close();

    }
}
