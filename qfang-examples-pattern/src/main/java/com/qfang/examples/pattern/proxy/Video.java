package com.qfang.examples.pattern.proxy;

/**
 * @author: liaozhicheng.cn@163.com
 * @date: 2018-04-01
 * @since: 1.0
 */
public interface Video {

    /**
     * 获取视频的描述，简介等信息
     * @return
     */
    String getDesc();

    /**
     * 播放视频
     */
    void play();

}
