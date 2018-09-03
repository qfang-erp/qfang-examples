package com.qfang.examples.java8.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

import org.assertj.core.util.Lists;
import org.junit.Test;

/**
 * 并行流
 * parallelismTest 并行流的内部实现方式，对于同一个元素的所有 stream 操作总是在一个线程内完成
 * parallelStreamStortTest 对于并行流的 sort 操作并不总是并行进行
 * reduceTest 并行流的 reduce 操作是并行进行，基于 reduce 的 max, min 也是并行进行
 *
 * @author liaozhicheng
 * @date 2016年7月29日
 * @since 1.0
 */
public class ParallelStreamTests {

    @Test
    public void parallelismTest() {
        // 并行流默认采用的 ForkJoinPool 实现
        ForkJoinPool commonPool = ForkJoinPool.commonPool();

        System.out.println(commonPool.getParallelism());    // 7

        // 默认的并行级别等于机器可用的cpu数量
        // 可以用下面的方式修改默认的并行级别
        // -Djava.util.concurrent.ForkJoinPool.common.parallelism=3
        Lists.newArrayList("a1", "a2", "b1", "c2", "c1", "d1", "f1", "c3", "h1")
                .parallelStream()
                .filter(s -> {
                    System.out.format("filter: %s [%s]\n", s, Thread.currentThread().getName());
                    return true;
                })
                .map(s -> {
                    System.out.format("map: %s [%s]\n", s, Thread.currentThread().getName());
                    return s.toUpperCase();
                })
                .forEach(s -> System.out.format("forEach: %s [%s]\n", s, Thread.currentThread().getName()));
    }

    @Test
    public void parallelStreamStortTest() {
        // filter,map 方法可以并行执行，但是 sort 方法却只在一个线程中串行执行

        // 并行流的 sort 采用的是 java8 里面的 Arrays.parallelSort() 方法实现
        // If the length of the specified array is less than the minimum
        // granularity, then it is sorted using the appropriate Arrays.sort() method.
        // 默认的阈值 1 << 13 （8192），即当排序元素的个数小于 8192 个时，默认采用的是串行排序
        Lists.newArrayList("a1", "a2", "b1", "c2", "c1")
                .parallelStream()
                .filter(s -> {
                    System.out.format("filter: %s [%s]\n", s, Thread.currentThread().getName());
                    return true;
                })
                .map(s -> {
                    System.out.format("map: %s [%s]\n", s, Thread.currentThread().getName());
                    return s.toUpperCase();
                })
                .sorted((s1, s2) -> {
                    System.out.format("sort: %s <> %s [%s]\n", s1, s2, Thread.currentThread().getName());
                    return s1.compareTo(s2);
                })
                .forEach(s -> System.out.format("forEach: %s [%s]\n", s, Thread.currentThread().getName()));
    }

    @Test
    public void reduceTest() {
        // reduce 方法可以并行执行，类似的 max,min 方法，底层采用的是 reduce 方法实现，也可以并行实现
        List<Person> persons = Arrays.asList(
                new Person("Max", 18),
                new Person("Peter", 23),
                new Person("Pamela", 23),
                new Person("David", 12));

        int ageSum = persons
                .stream()
                .parallel()
                .reduce(0, (sum, p) -> {
                    System.out.format("accumulator: sum=%s; person=%s [%s]\n",
                            sum, p, Thread.currentThread().getName());
                    return sum += p.age;
                }, (sum1, sum2) -> {
                    System.out.format("combiner: sum1=%s; sum2=%s [%s]\n",
                            sum1, sum2, Thread.currentThread().getName());
                    return sum1 + sum2;
                });
        System.out.println(ageSum);
    }

    @Test
    public void maxTest() {
        List<Integer> nums = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7);
        nums.parallelStream().max((x, y) -> {
            System.out.format("comparate: x=%s; y=%s [%s]\n", x, y, Thread.currentThread().getName());
            return x - y;
        });
    }

    @Test
    public void parallelCollectTest() {
        List<Integer> list = IntStream.range(0, 100)
                .filter(i -> i % 2 == 0)
                .parallel()
                .mapToObj(Integer::new)
                .collect(() -> {
                    System.out.println("new List thread: " + Thread.currentThread().getName());
                    return new ArrayList<Integer>();
                }, (l, i) -> {
                    System.out.format("add thread: %s, list: %s, value: %s \n", Thread.currentThread().getName(), l, i);
                    l.add(i);
                }, (l1, l2) -> {
//				System.out.format("add all thread: %s, list1: %s, list2: %s \n", Thread.currentThread().getName(), l1, l2);
                    l1.addAll(l2);
                });

        System.out.println(list.size());
    }

    private class Person {

        String name;
        int age;

        private Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return name;
        }

    }
    @Test
    public void test11(){
        List<Person> mypersons=new ArrayList<>();
        IntStream.range(1,1000).forEach(i->{
            mypersons.add(new Person("张三"+i,18+i));
        });
        exec(mypersons);
    }

    public static synchronized void exec(List<Person> list) {
        final List<List<Person>> allList=subList(list);
        final BlockingQueue<List<Person>> queues=new LinkedBlockingQueue<>(allList.size());
        final BlockingQueue<List<Person>> saveQueues=new LinkedBlockingQueue<>(allList.size());
        allList.forEach( ilist->{
            queues.add(ilist);
        });

        ExecutorService executorService1= Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<Boolean>> listFuture=new ArrayList<>();
        for(int i=0;i<Runtime.getRuntime().availableProcessors()/2;i++) {
            executorService1.execute(() -> {
                try {
                    for (; ; ) {
                        List<Person> persons = queues.poll(10,TimeUnit.SECONDS);
                        if (persons == null) {
                            break;
                        }
                        //处理查询第三方数据;
                        saveQueues.add(persons);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            listFuture.add(executorService1.submit(()->{
                try{
                    for (;;){
                        List<Person> savepersons = saveQueues.poll(2,TimeUnit.SECONDS);
                        if(savepersons==null){
                            break;
                        }
                        //保存数据到数据库;

                    }

                }catch (Exception ex){
                    ex.printStackTrace();
                }
                return true;
            }));
        }
     //   saveQueues.
        for(int i=0;i<listFuture.size();i++){
            try {
               listFuture.get(i).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        System.out.printf("seccuess");
    }

    public static  <T> List<List<T>> subList(List<T> list) {
        final List<List<T>> allList = new ArrayList<>();
        final int step = 50;
        for (int i = 0; i < list.size(); i = i + step) {
            int endIndex=i + step> list.size()?list.size():i+step;
            allList.add(list.subList(i,endIndex ));
        }
        return allList;
    }

}
