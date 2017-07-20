package com.qfang.examples.java8.stream;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年7月26日
 * @since 1.0
 */
public class TextWord {
	
	public static List<String> words() {
		List<String> words = new ArrayList<String>();
		try {
			String pathName = TextWord.class.getClassLoader().getResource("WindsOfWar.txt").getPath();
			String contents = new String(Files.readAllBytes(Paths.get(pathName.replaceFirst("/", ""))), StandardCharsets.UTF_8);
			words = Arrays.asList(contents.split("[\\P{L}]+"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return words;
	}
	
}
