package com.qfang.examples.springbatch.dto;

/**
 * @author huxianyong
 * @date 2018/7/31
 * @since 1.0
 */
public class PersonDto {
    private String id;
    private String name;
    private Integer age;
    private String  onJob;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getOnJob() {
        return onJob;
    }

    public void setOnJob(String onJob) {
        this.onJob = onJob;
    }
}
