package com.qfang.examples.spring.ch8.homework;

import java.io.File;
import java.util.stream.Stream;

/**
 * Created by walle on 2017/4/30.
 */
public class FileSearcher {

    private static final String PATH = "D:\\workspace-idea\\jaf-examples\\jaf-examples-spring\\src\\main\\java\\com\\jaf\\examples\\spring";

    public void search(String suffix) {
        File file = new File(PATH);
        File[] matchFiles = file.listFiles((File dir, String name) -> name.endsWith(suffix));
        Stream.of(matchFiles).forEach(f -> System.out.println(f.getName()));
    }

}
