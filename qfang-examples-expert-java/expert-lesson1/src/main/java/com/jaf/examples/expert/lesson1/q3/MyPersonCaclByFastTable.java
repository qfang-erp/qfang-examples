package com.jaf.examples.expert.lesson1.q3;

import javolution.util.FastTable;

import java.io.IOException;

/**
 * @author liaozhicheng.cn@163.com
 */
class MyPersonCaclByFastTable extends AbstractCalcelator {


    public static void main(String[] args) throws IOException {
        final FastTable<Person> persons = new FastTable<>();
        loadFromDbFile(persons);
        groupBy(persons);
    }


}
