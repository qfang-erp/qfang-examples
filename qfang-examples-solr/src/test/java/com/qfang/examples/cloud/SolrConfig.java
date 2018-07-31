package com.qfang.examples.cloud;

/**
 * @author huxianyong
 * @date 2017/7/20
 * @since 1.0
 */
public class SolrConfig {

    private String zkHost;
    private String defaultCollection;
    private Integer zkClientTimeout;
    private Integer zkConnectTimeout;

    public String getKey(){
        StringBuilder  sb=new StringBuilder();
        sb.append(this.getZkHost()).append(this.getDefaultCollection());
        return sb.toString();
    }

    public String getZkHost() {
        return zkHost;
    }

    public void setZkHost(String zkHost) {
        this.zkHost = zkHost;
    }

    public String getDefaultCollection() {
        return defaultCollection;
    }

    public void setDefaultCollection(String defaultCollection) {
        this.defaultCollection = defaultCollection;
    }

    public Integer getZkClientTimeout() {
        return zkClientTimeout;
    }

    public void setZkClientTimeout(Integer zkClientTimeout) {
        this.zkClientTimeout = zkClientTimeout;
    }

    public Integer getZkConnectTimeout() {
        return zkConnectTimeout;
    }

    public void setZkConnectTimeout(Integer zkConnectTimeout) {
        this.zkConnectTimeout = zkConnectTimeout;
    }
}
