package com.jaf.examples.expert.lesson7.q2;

import com.jaf.examples.expert.common.utils.UnsafeUtils;
import sun.misc.Unsafe;

import java.lang.reflect.Constructor;

/**
 * @author liaozhicheng.cn@163.com
 */
public class MyClass {

    private final long a = 10;

    public MyClass() {
    }

    public static void main(String[] args) throws Exception {
        Unsafe unsafe = UnsafeUtils.getUnsafe();
        MyClass obj = (MyClass) unsafe.allocateInstance(MyClass.class);
        System.out.println(obj.a);

        MyClass obj2 = MyClass.class.newInstance();
        System.out.println(obj2.a);

        Constructor<MyClass> con = MyClass.class.getConstructor();
        MyClass obj3 = con.newInstance();
        System.out.println(obj3.a);
    }

}
