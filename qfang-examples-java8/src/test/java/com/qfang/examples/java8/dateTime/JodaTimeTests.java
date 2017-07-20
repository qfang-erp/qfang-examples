package com.qfang.examples.java8.dateTime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

/**
 * Joda Time 的一些基本用法
 * 官方主页：http://www.joda.org/joda-time/
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年7月30日
 * @since 1.0
 */
public class JodaTimeTests {
	
	private static final String DEFAULT_DATE_FORMATTER = "yyyy-MM-dd HH:mm:ss";
	private static final String DATE_FORMATTER_YMD = "yyyy-MM-dd";
	private static final String DATE_FORMATTER_YM = "yyyy-MM";
	
	@Test
	public void baseTest() {
		DateTime now = new DateTime();  // 取的当前时间
		println(now);

		// 年,月,日,时,分,秒,毫秒  方式初始化时间
		DateTime date1 = new DateTime(2016, 7, 30, 20, 20, 20, 333);
		println(date1);

		// 时间解析
		DateTimeFormatter format = DateTimeFormat .forPattern(DEFAULT_DATE_FORMATTER);
		DateTime dateTime2 = DateTime.parse("2012-12-21 23:22:45", format);
		println(dateTime2);

		// 前一天，后一天
		println(DateTime.now().minusDays(1));
		println(DateTime.now().plusDays(1));

		// 1个月后，1年后
		println(DateTime.now().plusMonths(1));
		println(DateTime.now().plusYears(1));

		// 当前时间所在周的第一天和最后一天
		println(DateTime.now().dayOfWeek().withMinimumValue());
		println(DateTime.now().dayOfWeek().withMaximumValue());

		// 当前月的第一天，当前年最后一天，当前月第二周的最后一天(当前月第一天，往后推一周的最大日期)
		println(DateTime.now().dayOfMonth().withMinimumValue());
		println(DateTime.now().dayOfYear().withMaximumValue());
		println(DateTime.now().withDayOfMonth(1).plusWeeks(1).dayOfWeek().withMaximumValue());
		
		// 根据字符串月份，获取该月份的最后一天
		String month = "2016-08";
		String monthMaxDate = DateTime.parse(month, DateTimeFormat.forPattern(DATE_FORMATTER_YM))
				.dayOfMonth().withMaximumValue().toString(DATE_FORMATTER_YMD);
		System.out.println("monthMaxDate : " + monthMaxDate);
		
		// 当前日期 2016-07-30，获取当前日期在3月份的日期，即 2016-03-30
		println(DateTime.now().monthOfYear().setCopy(3));
		println(DateTime.now().monthOfYear().setCopy(2));  // 2月没有30号，这里得到的是 2016-02-29
		println(DateTime.now().monthOfYear().addToCopy(-4));  // 另一种实现方式

		// 打印当前月的所有日期
		System.out.println("======= days of month ==========");
		List<Long> daysOfMonth = getDaysOfMonth(DateTime.now());
		daysOfMonth.stream().map(input -> {
			return new DateTime(input.longValue());
		}).forEach(this::printlnYMD);

		// 时间计算
		DateTime lastDayOfYear = DateTime.now().dayOfYear().withMaximumValue();
		Duration duration = new Duration(DateTime.now(), lastDayOfYear);
		System.out.println("到年底还有多少天：" + duration.getStandardDays());
		System.out.println("到年底还有多少个小时：" + duration.getStandardHours());
	}
	
	private void println(DateTime dateTime) {
		System.out.println(dateTime.toString(DEFAULT_DATE_FORMATTER));
	}
	
	private void printlnYMD(DateTime dateTime) {
		System.out.println(dateTime.toString(DATE_FORMATTER_YMD));
	}
	
	/**
	 * 获取当前月所有的日期列表
	 * @param date
	 * @return
	 */
	public static List<Long> getDaysOfMonth(DateTime date) {
		final ImmutableList.Builder<Long> dayList = ImmutableList.builder();
		
		LocalDate firstDayOfMonth = date.toLocalDate().withDayOfMonth(1);
		final LocalDate nextMonthFirstDay = firstDayOfMonth.plusMonths(1);
		while (firstDayOfMonth.isBefore(nextMonthFirstDay)) {
			dayList.add(firstDayOfMonth.toDateTimeAtStartOfDay().getMillis());
			firstDayOfMonth = firstDayOfMonth.plusDays(1);
		}
		return dayList.build();
	}
	
	
	@Test
	public void intervalTest() {
		// 获取当前日期到本月最后一天还剩多少天
		LocalDate now = LocalDate.now();
		LocalDate lastDayOfMonth = LocalDate.now().dayOfMonth().withMaximumValue();
		System.out.println(Days.daysBetween(now, lastDayOfMonth).getDays());
		
		System.out.println(DateTime.now().toString(DEFAULT_DATE_FORMATTER));
		System.out.println(DateTime.now().dayOfMonth().withMaximumValue().toString(DEFAULT_DATE_FORMATTER));
		Interval interval = new Interval(DateTime.now(), DateTime.now().dayOfMonth().withMaximumValue());
		Period p = interval.toPeriod();
		
		System.out.println(interval.toDuration().getStandardDays());
		
		System.out.format("时间相差：%s 年, %s 月, %s 天, %s 时, %s 分, %s 秒", 
				p.getYears(), p.getMonths(), p.getDays(), p.getHours(), p.getMinutes(), p.getSeconds());
	}
	
	@Test
	public void dateConvert() {
		// joda date time -> java.util.Date
		Date date = DateTime.now().plusDays(1).toDate();
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMATTER);
		System.out.println(sdf.format(date));
		
		// java.util.Date -> joda date
		Date d1 = new Date();
		LocalDate ld = new LocalDate(d1);
		System.out.println(ld.toString(DATE_FORMATTER_YMD));
		
		DateTime dateTime = new DateTime(d1);
		System.out.println(dateTime.toString(DEFAULT_DATE_FORMATTER));
	}
	
}
