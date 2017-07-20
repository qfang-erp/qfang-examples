package com.jaf.examples.expert.lesson1.q3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2016年8月11日
 * @since 1.0
 */
class MyPersonCaclByArrayList extends AbstractCalcelator {

	public static void main(String[] args) throws IOException {
		final List<Person> persons = new ArrayList<>();
		loadFromDbFile(persons);
		
		groupBy(persons);
//		groupByConcurrent();
	}


}
