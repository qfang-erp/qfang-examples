package com.qfang.examples.springbatch.dto;


import java.io.Serializable;

/**
 * @author huxianyong
 * @date 2018/7/31
 * @since 1.0
 */
public class ReportDto implements Serializable {

    private String id;
    private String tenementDetail;
    private String personName;
    private String orgName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenementDetail() {
        return tenementDetail;
    }

    public void setTenementDetail(String tenementDetail) {
        this.tenementDetail = tenementDetail;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}
