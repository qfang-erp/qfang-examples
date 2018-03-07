package com.qfang.examples.ribbonconsumer;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.qfang.examples.ribbonconsumer.service.CunsumerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author huxianyong
 * @date 2018/3/2
 * @since 1.0
 */
@RestController
public class CunsumerController {

    @Resource
    CunsumerService cunsumerService;


    @GetMapping(value="/ribbon")
    public String testRibbon(){
        return cunsumerService.testRibbon();
    }


}
