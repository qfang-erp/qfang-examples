package com.qfang.examples.pattern.chainOfResponsibility;

/**
 * @author: liaozhicheng.cn@163.com
 * @date: 2018-03-10
 * @since: 1.0
 */
public class Message {

    public static int INFO = 1;
    public static int DEBUG = 2;

    private final int level;
    private final String content;

    public Message(int level, String content) {
        this.level = level;
        this.content = content;
    }

    public int getLevel() {
        return level;
    }

    public String getContent() {
        return content;
    }
}
