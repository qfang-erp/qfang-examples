package com.qfang.examples.java8.other;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;
import java.util.stream.Stream;

import org.junit.Test;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年9月5日
 * @since 1.0
 */
public class OtherTests {
	
	@Test
	public void stringTest() throws IOException {
		String joined = String.join(",", "a", "b", "c");
		System.out.println(joined);  // a,b,c
		
		Files.list(Paths.get("d:/temp")).forEach(System.out::println);
		
		try (Stream<String> lines = Files.lines(Paths.get("d:/temp/data.txt"))) {
			lines.forEach(System.out::println);
		}  // 这里会首先关闭流，然后关闭文件
		
		String x = "1", y = "2";
		Logger logger = Logger.getGlobal();
		logger.warning("x: " + x + ", y : " + y);
		
		logger.warning(() -> "x: " + x + ", y : " + y);
	}
	
	
}
