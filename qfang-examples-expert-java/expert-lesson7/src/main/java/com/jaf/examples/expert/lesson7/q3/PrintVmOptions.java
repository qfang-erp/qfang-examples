package com.jaf.examples.expert.lesson7.q3;

import com.sun.management.HotSpotDiagnosticMXBean;
import com.sun.management.VMOption;
import sun.management.ManagementFactoryHelper;

/**
 * @author liaozhicheng.cn@163.com
 */
public class PrintVmOptions {

    public static void main(String[] args) {
        HotSpotDiagnosticMXBean hsd = ManagementFactoryHelper.getDiagnosticMXBean();

        // 打印虚拟机的某个配置参数的值
        VMOption vmo = hsd.getVMOption("UseCompressedOops");
        System.out.println(vmo.getName() + " | " + vmo.getValue());

        hsd.getDiagnosticOptions().stream().forEach(System.out::println);
    }

}
