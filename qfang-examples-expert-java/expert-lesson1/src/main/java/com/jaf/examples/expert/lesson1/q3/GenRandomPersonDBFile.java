package com.jaf.examples.expert.lesson1.q3;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2016年8月11日
 * @since 1.0
 */
class GenRandomPersonDBFile {
	
	public static void generate() throws IOException {
		List<String> lines = new ArrayList<>();
		for(int i = 1; i <= Config.TOTAL_ROWS; i++) {
			StringBuilder lineBuilder = new StringBuilder();
			lineBuilder
				.append(i).append(Config.SPLIT_CHAR)
				.append(RandomStringUtils.randomAlphanumeric(8)).append(Config.SPLIT_CHAR)
				.append(RandomUtils.nextInt(1, 19));
			lines.add(lineBuilder.toString());
		}
		
		File file = new File(Config.DB_FILE_NAME);
		FileUtils.writeLines(file, lines);
	}

}
