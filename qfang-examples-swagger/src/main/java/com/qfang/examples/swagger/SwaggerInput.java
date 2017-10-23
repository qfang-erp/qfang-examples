package com.qfang.examples.swagger;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author huxianyong
 * @date 2017/10/20
 * @since 1.0
 */
@ApiModel("输入类")
public class SwaggerInput {
    @ApiModelProperty(" ID ")
    private String id;
    @ApiModelProperty(" 姓名 ")
    private String name;

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
}
