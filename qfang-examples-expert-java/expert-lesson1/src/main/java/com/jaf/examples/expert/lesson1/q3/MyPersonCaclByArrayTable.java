package com.jaf.examples.expert.lesson1.q3;

import com.google.common.collect.ArrayTable;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2016年8月11日
 * @since 1.0
 */
class MyPersonCaclByArrayTable extends AbstractCalcelator {

	public static void main(String[] args) throws IOException {
        arrayTable();
	}


	private static void loadFromDb(ArrayTable<Integer, Character, List<Person>> personsTable) throws IOException {
        long start = System.currentTimeMillis();

        File dbFile = new File(Config.DB_FILE_NAME);
        LineIterator it = FileUtils.lineIterator(dbFile, "UTF-8");
        while(it.hasNext()) {
            String line = it.next();
            Person person = parseStringTokenizer(line);
            char fc = person.name.charAt(0);
            List<Person> persons = personsTable.get(person.age, fc);
            if(persons == null) {
                persons = new ArrayList<>();
                personsTable.put(person.age, person.name.charAt(0), persons);
            }
            persons.add(person);
        }
        System.out.println("load data elapsed time : " + (System.currentTimeMillis() - start));
    }
	
	private static void arrayTable() throws IOException {
        List<Integer> ages = new ArrayList<>();
        for(int i = 1; i <= 18; i++) {
            ages.add(i);
        }

        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        List<Character> chapters = new ArrayList<>();
        for(int i = 0; i < str.length(); i++) {
            chapters.add(str.charAt(i));
        }

        ArrayTable<Integer, Character, List<Person>> personsTable = ArrayTable.create(ages, chapters);
		loadFromDb(personsTable);

        for(char c = 'a'; c <= 'e'; c++) {
            Map<Integer, List<Person>> rowsPerson = personsTable.column(c);
            long count6_11 = rowsPerson.keySet()
                    .stream()
                    .filter(r -> r >= 6 && r <= 11)
                    .map(r -> rowsPerson.get(r).size()).reduce(0, Integer::sum);
            System.out.println("[6-11]-" + c + " : " + count6_11);

            long count16_18 = rowsPerson.keySet()
                    .stream()
                    .filter(r -> r >= 16 && r <= 18)
                    .map(r -> rowsPerson.get(r).size()).reduce(0, Integer::sum);
            System.out.println("[16-18]-" + c + " : " + count16_18);
        }

	}
	

}
