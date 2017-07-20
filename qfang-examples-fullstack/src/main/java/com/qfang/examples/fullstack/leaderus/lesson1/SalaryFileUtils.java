package com.qfang.examples.fullstack.leaderus.lesson1;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import com.qfang.examples.fullstack.leaderus.common.Config;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年9月3日
 * @since 1.0
 */
public class SalaryFileUtils {
	
	public static void writeFile(int lines) throws IOException {
		List<String> salaryLines = IntStream.range(0, lines)
				.mapToObj(SalaryFileUtils::randomSalaryLine)
				.collect(Collectors.toList());
		
		File file = new File(Config.DB_FILE_NAME);
		FileUtils.writeLines(file, salaryLines);
		
	}
	
	private static String randomSalaryLine(int i) {
		return new StringBuilder()
			.append(RandomStringUtils.randomAlphanumeric(8)).append(Config.SPLIT_CHARACTER)
			.append(RandomUtils.nextInt(5, 1000000)).append(Config.SPLIT_CHARACTER)
			.append(RandomUtils.nextInt(0, 100000))
			.toString();
	}
	
	public static List<Salary> readFromFile() throws IOException {
		List<Salary> salarys = new ArrayList<>();
		
        File dbFile = new File(Config.DB_FILE_NAME);
        LineIterator it = FileUtils.lineIterator(dbFile, "UTF-8");
        it.forEachRemaining(str -> {
        	salarys.add(parseStringTokenizer(str));
        });
        return salarys;
	}
	
    private static Salary parseStringTokenizer(String line) {
        // StringTokenizer 比 String.split 快一倍
        StringTokenizer token = new StringTokenizer(line, Config.SPLIT_CHARACTER);
        Salary salary = new Salary(token.nextToken(), 
        		Integer.valueOf(token.nextToken()), Integer.valueOf(token.nextToken()));
        return salary;
    }
	
    public static void main(String[] args) throws IOException {
    	SalaryFileUtils.writeFile(10000000);
	}
    
}
