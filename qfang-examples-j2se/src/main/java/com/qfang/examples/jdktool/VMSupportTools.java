package com.qfang.examples.jdktool;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.util.VMSupport;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2016年11月14日
 * @since 1.0
 */
public class VMSupportTools {

	public static void main(String[] args) {
		System.out.println(VMSupport.vmDetails());
		System.out.println(ClassLayout.parseClass(Integer.class).toPrintable());
	}

}
