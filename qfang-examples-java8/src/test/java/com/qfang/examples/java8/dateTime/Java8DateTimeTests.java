package com.qfang.examples.java8.dateTime;

import java.sql.Time;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

import org.junit.Test;

/**
 * java 8 里面新的日期和时间 API 很大程度上和 Joda-Time 的 API 类似
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年7月30日
 * @since 1.0
 */
public class Java8DateTimeTests {
	
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	@Test
	public void localDateTest() {
		LocalDate date = LocalDate.of(2016, 9, 4); // 9月也可以用 Month.SEPTEMBER 表示
		System.out.println(date);  // 2016-09-04
		System.out.println(LocalDate.now());  // 2016-09-04
		
		// 当前日期前一天，后一天
		System.out.println(date.minusDays(1));  // 2016-09-03
		System.out.println(date.plusDays(1));  // 2016-09-05
		
		// 当前日期所在月份的第一天（两种方式效果等同）和最后一天
		System.out.println(date.withDayOfMonth(1));  // 2016-09-01
		System.out.println(date.with(TemporalAdjusters.firstDayOfMonth()));  // 2016-09-01
		System.out.println(date.with(TemporalAdjusters.lastDayOfMonth()));  // 2016-09-30
		
		// 当前日期下个月的第一天
		System.out.println(date.with(TemporalAdjusters.firstDayOfNextMonth()));  // 2016-10-01
		
		// 当前日期离月底还有多少天
		LocalDate lastDayOfMonth = date.with(TemporalAdjusters.lastDayOfMonth());
		System.out.println(date.until(lastDayOfMonth).getDays());  // 26
		
		// 将 LocalDate 转换成 LocalDateTime
		LocalDateTime dateTime = date.atTime(20, 11, 11);
		System.out.println(dateTime.format(formatter));  // 2016-09-04 20:11:11
		
		// 因为 java.time.LocalDate 只包含日期信息，因此不能直接转成 java.util.Date
		// 但可以直接转换成 java.sql.Date
		java.sql.Date sqlDate = java.sql.Date.valueOf(date);
		System.out.println(sqlDate);  // 2016-09-04
	}
	
	@Test
	public void localTimeTest() {
		LocalTime time = LocalTime.of(20, 11, 11);
		System.out.println(time);  // 20:11:11
		System.out.println(LocalTime.now());  // 23:22:31.030
		
		Time sqlTime = Time.valueOf(time);
		System.out.println(sqlTime);  // 20:11:11
	}
	
	@Test
	public void localDateTimeTest() throws InterruptedException {
		LocalDateTime begin = LocalDateTime.now();
		System.out.println(begin.format(formatter));  // 2016-09-04 23:30:09
		
		Thread.sleep(1000);
		LocalDateTime end = LocalDateTime.now();
		
		// 获取两个时间的间隔
		Duration d = Duration.between(begin, end);
		System.out.println(d.getSeconds());  // 1
	}
	
	@Test
	public void instantTest() {
		Instant instant = Instant.now();
		Date date = Date.from(instant);  // instant -> java.util.Date
		System.out.println(date);
	}
	
}
