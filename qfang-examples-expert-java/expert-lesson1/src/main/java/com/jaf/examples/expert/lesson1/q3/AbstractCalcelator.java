package com.jaf.examples.expert.lesson1.q3;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * @author liaozhicheng.cn@163.com
 */
abstract class AbstractCalcelator {

    protected static void loadFromDbFile(Collection<Person> persons) throws IOException {
        long start = System.currentTimeMillis();

        File dbFile = new File(Config.DB_FILE_NAME);
        LineIterator it = FileUtils.lineIterator(dbFile, "UTF-8");
        while(it.hasNext()) {
            String line = it.next();
            persons.add(parseStringTokenizer(line));
        }

        System.out.println("load data elapsed time : " + (System.currentTimeMillis() - start));
    }


    protected static void groupBy(Collection<Person> persons) {
        long start = System.currentTimeMillis();
        Map<String, Long> result = persons.stream()
                .filter(AbstractCalcelator::match)
                .collect(
                        Collectors.groupingBy(
                                p -> (p.age >= 6 && p.age <= 11 ? "[6-11]-" : "[16-18]-") + p.name.charAt(0),
                                Collectors.counting()
                        )
                );
        long end = System.currentTimeMillis();
        System.out.println(result);
        System.out.println("elapsed time : " + (end - start));
    }

    protected static void groupByConcurrent(Collection<Person> persons) {
        long start = System.currentTimeMillis();
        ConcurrentMap<String, Long> result = persons.parallelStream()
                .filter(AbstractCalcelator::match)
                .collect(
                        Collectors.groupingByConcurrent(
                                p -> (p.age >= 6 && p.age <= 11 ? "[6-11]-" : "[16-18]-") + p.name.charAt(0),
                                Collectors.counting()
                        )
                );
        long end = System.currentTimeMillis();
        System.out.println(result);
        System.out.println("elapsed time : " + (end - start));
    }

    protected static Person parseSplit(String line) {
        String[] props = line.split(",");
        Person person = new Person(Integer.valueOf(props[0]), props[1], Integer.valueOf(props[2]));
        return person;
    }

    protected static Person parseStringTokenizer(String line) {
        // StringTokenizer 比 String.split 快一倍
        StringTokenizer token = new StringTokenizer(line, ",");
        Person person = new Person(Integer.valueOf(token.nextToken()), token.nextToken(), Integer.valueOf(token.nextToken()));
        return person;
    }


    protected static boolean matchRegex(Person p) {
        return p.name.matches("^[a|b|c|d|e]\\w*")
                && ((p.age >= 6 && p.age <= 11) || (p.age >= 16 && p.age <= 18));
    }

    protected static boolean match(Person p) {
        // 这两种匹配方式，性能相差10倍
        char fc = p.name.charAt(0);
        int age = p.age;
        return (fc == 'a' || fc == 'b' || fc == 'c' || fc == 'd' || fc == 'e')
                && ((age >= 6 && age <= 11) || (age >= 16 && age <= 18));
    }

}
