package com.qfang.examples.pattern.proxy;

/**
 * @author: liaozhicheng.cn@163.com
 * @date: 2018-04-01
 * @since: 1.0
 */
public class ProxyVideo implements Video {

    private final String desc;  // 视频描述信息
    private final String resourceUrl;  // 视频资源地址

    private DefaultVideo target;

    public ProxyVideo(String desc, String resourceUrl) {
        this.desc = desc;
        this.resourceUrl = resourceUrl;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }

    @Override
    public void play() {
        if(this.target == null)
            this.target = new DefaultVideo(desc, resourceUrl);

        this.target.play();
    }

}
