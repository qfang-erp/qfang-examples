package com.jaf.examples.expert.lesson6.q1;

import com.jaf.examples.expert.common.utils.UnsafeUtils;
import sun.misc.Unsafe;

import java.util.Arrays;

/**
 * 使用 Unsafe API 操作数组中的元素
 *
 * @author liaozhicheng.cn@163.com
 */
public class UnsafeOperateArray {

    static long[] la = new long[5];


    public static void main(String[] args) {
        Unsafe unsafe = UnsafeUtils.getUnsafe();

        la[0] = 2;
        System.out.println(Arrays.toString(la));

        // 获取数值中第一个元素的偏移量地址
        long bof = unsafe.arrayBaseOffset(long[].class);
        // 获取数值中每个元素地址的增量（每个元素占多少 byte）
        long is = unsafe.arrayIndexScale(long[].class);

        // arrayBaseOffset, arrayIndexScale 这两个参数配合使用就可以获取数组中任意一个元素的地址
        unsafe.putLong(la, bof, 0L);
        unsafe.putLong(la, bof + is, 1L);
        unsafe.putLong(la, bof + is * 2, 2L);
        System.out.println(Arrays.toString(la));  // 0,1,2,0,0
    }


}
