package com.qfang.examples.pattern.command;

/**
 * @author: liaozhicheng.cn@163.com
 * @date: 2018-03-10
 * @since: 1.0
 */
public class TestMain {

    public static void main(String[] args) {
        CompositeCommand recordCommand = new AutoRecordCommand();
        recordCommand.addCommand(new OpenTvCommand());
        recordCommand.addCommand(new TransformChannelCommand());
        recordCommand.addCommand(new TurnOffCommand());

        Invoker invoker = new Invoker(new Tv(), recordCommand);
        invoker.invoke();
    }

}
