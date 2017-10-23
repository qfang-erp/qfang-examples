package com.qfang.examples.swagger;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author huxianyong
 * @date 2017/10/20
 * @since 1.0
 */
@Controller
@RequestMapping(value = "/stat")
public class SwaggerController {
    @ResponseBody
    @RequestMapping(value = "/helloworld", method = RequestMethod.GET)
    @ApiOperation(nickname = "swagger-helloworld", value = "Swagger的世界", notes = "测试HelloWorld")
    public String helloWorld(@ApiParam(value = "昵称") @RequestParam String nickname) {
        return "Hello world, " + nickname;
    }

    @ResponseBody
    @RequestMapping(value = "/objectio", method = RequestMethod.POST)
    @ApiOperation(nickname = "swagger-objectio", value = "Swagger的ObjectIO", notes = "测试对象输入输出")
    public SwaggerOutput objectIo(@ApiParam(value = "输入") @RequestBody SwaggerInput input) {
        SwaggerOutput output = new SwaggerOutput();
        output.setId(input.getId());
        output.setName("Swagger");
        return output;
    }
}
