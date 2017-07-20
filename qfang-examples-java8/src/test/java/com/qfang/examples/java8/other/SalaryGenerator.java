package com.qfang.examples.java8.other;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年9月3日
 * @since 1.0
 */
public class SalaryGenerator {
	
	public static List<Salary> random(int size) {
		if(size <= 0)
			return new ArrayList<Salary>();
		
		List<Salary> list = new ArrayList<>(size);
		random(size, list);
		return list;
	}
	
	public static void random(int size, Collection<Salary> collection) {
		IntStream.range(0, size)
			.mapToObj(SalaryGenerator::createSalary)
			.collect(Collectors.toCollection(() -> collection));
	}
	
	public static List<Salary> parallelRandom(int size) {
		List<Salary> list = new ArrayList<>();
		parallelRandom(size, list);
		return list;
	}
	
	public static void parallelRandom(int size, Collection<Salary> collection) {
		Map<String, Salary> map = IntStream.range(0, size)
			.parallel()
			.mapToObj(SalaryGenerator::createSalary)
			.collect(Collectors.toConcurrentMap(Salary::getName, Function.identity()));
		collection.addAll(map.values());
	}
	
	private static Salary createSalary(int i) {
		Salary salary = new Salary();
		salary.setName(RandomStringUtils.randomAlphanumeric(8));
		salary.setBaseSalary(RandomUtils.nextInt(5, 1000000));
		salary.setBonus(RandomUtils.nextInt(0, 100000));
		return salary;
	}
	
	
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		List<Salary> list = random(1000000);
		System.out.println("elasped time: " + (System.currentTimeMillis() - start) + ", list size : " + list.size());
	}
	
}
