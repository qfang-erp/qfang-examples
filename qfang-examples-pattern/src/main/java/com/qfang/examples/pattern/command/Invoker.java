package com.qfang.examples.pattern.command;

/**
 * @author: liaozhicheng.cn@163.com
 * @date: 2018-03-10
 * @since: 1.0
 */
public class Invoker {

    private final Tv tv;
    private final TvCommand tvCommand;

    public Invoker(Tv tv, TvCommand tvCommand) {
        this.tv = tv;
        this.tvCommand = tvCommand;
    }

    public void invoke() {
        tvCommand.execute(tv);
    }

}
