package com.jaf.examples.expert.lesson7.q3;

import com.sun.management.VMOption;
import net.bramp.unsafe.UnsafeHelper;
import sun.management.HotSpotDiagnostic;

import com.sun.management.HotSpotDiagnosticMXBean;
import sun.management.ManagementFactoryHelper;
import com.sun.management.VMOption;


/**
 * @author liaozhicheng.cn@163.com
 */
public class UnsafeArrayListUtils {

    public static void main(String[] args) {
        C c = new C();
        long addr = UnsafeHelper.toAddress(c);
        System.out.println(addr);

        System.out.println(UnsafeHelper.sizeOf(c));




    }


    static class C {

        int x;
        int y;

    }

}
