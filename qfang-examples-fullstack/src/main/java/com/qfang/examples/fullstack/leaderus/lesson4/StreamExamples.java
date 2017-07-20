package com.qfang.examples.fullstack.leaderus.lesson4;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.stream.Collectors;

import com.google.common.collect.HashBiMap;
import com.qfang.examples.fullstack.leaderus.lesson1.Salary;
import com.qfang.examples.fullstack.leaderus.lesson1.SalaryFileUtils;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年9月3日
 * @since 1.0
 */
public class StreamExamples {
	
	public static void main(String[] args) throws IOException {
		List<Salary> list = SalaryFileUtils.readFromFile();
		
		long start = System.currentTimeMillis();
		HashBiMap<String, LongSummaryStatistics> groupMap = list.parallelStream()
			.filter(s -> s.getTotalIncome() > 100000)
			.collect(
				Collectors.groupingBy(
					Salary::namePrefix,
					() -> HashBiMap.create(),
					Collectors.summarizingLong(Salary::getTotalIncome)
				)
			);
		
		groupMap.values()
			.parallelStream()
			.sorted(Comparator.comparingLong(LongSummaryStatistics::getSum).reversed()) // 默认是从小到大排序
			.limit(10)
			.forEachOrdered(ls -> {
				System.out.format("[%s], count: %s, sum: %s \n", 
						groupMap.inverse().get(ls), ls.getCount(), ls.getSum());
			});
		System.out.println("elapsed time : " + (System.currentTimeMillis() - start));
	}
	
}
