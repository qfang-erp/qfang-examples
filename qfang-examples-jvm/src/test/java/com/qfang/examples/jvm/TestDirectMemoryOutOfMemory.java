package com.qfang.examples.jvm;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;

/**
 * 测试直接内存溢出
 *
 * @author huxianyong
 * @create 2017-08-05 下午 12:18
 * JVM参数  -verbose:gc -Xmx4g -Xms4g -Xss1m -XX:+PrintGCDetails -XX:MaxDirectMemorySize=10M
 */
public class TestDirectMemoryOutOfMemory {

    private static final int ONE_MB=1024*1024;
    private static int count = 1;
    public static void main(String[] args) {
        List<ByteBuffer> list=new LinkedList<>();
        while(true) {
                ByteBuffer bb = ByteBuffer.allocateDirect(ONE_MB);
                list.add(bb);
                System.out.println("Current Direct Memory size ：" + list.size() + "M");

        }
    }

//
//    public static void main(String[] args) {
//        try {
//            Field field= Unsafe.class.getDeclaredField("theUnsafe");
//            field.setAccessible(true);
//            Unsafe unsafe= (Unsafe) field.get(null);
//            while (true) {
//                unsafe.allocateMemory(ONE_MB);
//            }
////            long nameOffset= unsafe.objectFieldOffset(Person.class.getDeclaredField("name"));
////
////            Person person =new Person();
////            person.setName("张三");
////
////            unsafe.compareAndSwapObject(person,nameOffset,"张三","李四");
////            System.out.println(person.getName());
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//

}
