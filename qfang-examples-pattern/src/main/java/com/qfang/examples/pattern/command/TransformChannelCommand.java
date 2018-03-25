package com.qfang.examples.pattern.command;

/**
 * @author: liaozhicheng.cn@163.com
 * @date: 2018-03-10
 * @since: 1.0
 */
public class TransformChannelCommand implements TvCommand {

    @Override
    public void execute(Tv tv) {
        System.out.println("切换频道，开始播放视频...");
    }

}
