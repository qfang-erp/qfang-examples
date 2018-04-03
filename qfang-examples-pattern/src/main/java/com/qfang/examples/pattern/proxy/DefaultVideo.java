package com.qfang.examples.pattern.proxy;

/**
 * @author: liaozhicheng.cn@163.com
 * @date: 2018-04-01
 * @since: 1.0
 */
public class DefaultVideo implements Video {

    private final String desc;  // 视频描述信息
    private final String resourceUrl;  // 视频资源地址

    private final byte[] content; // 真正的视频资源信息

    public DefaultVideo(String desc, String resourceUrl) {
        this.desc = desc;
        this.resourceUrl = resourceUrl;

        // 为了能够加速播放，视频对象创建出来之后，立刻加载视频资源
        this.content = this.doLoad();
    }

    @Override
    public String getDesc() {
        return this.desc;
    }

    @Override
    public void play() {
        System.out.println("播放视频....");
    }

    private byte[] doLoad() {
        System.out.println("从视频资源地址下载视频");
        return new byte[10];
    }

}
