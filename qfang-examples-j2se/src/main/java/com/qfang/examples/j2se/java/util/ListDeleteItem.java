package com.qfang.examples.j2se.java.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * list 中正确的迭代和删除元素
 * 参考：http://haohaoxuexi.iteye.com/blog/1523785
 * http://swiftlet.net/archives/743
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年8月28日
 * @since 1.0
 */
public class ListDeleteItem {
	
	
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.addAll(Arrays.asList("a,b,b,c,c,c".split(",")));
		remove1(list);
		list.stream().forEach(System.out::println);
	}
	
	
	private static void remove1(List<String> list) {
		for (int i = 0; i < list.size(); i++) {
			String s = list.get(i);
			if (s.equals("b")) {
				list.remove(s);
			}
		}
	}
	
}
