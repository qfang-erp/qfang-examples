package com.qfang.examples.ribbonconsumer.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author huxianyong
 * @date 2018/3/5
 * @since 1.0
 */
@Service
public class CunsumerService {

    @Resource
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "testFallback")
    public String testRibbon(){
        return restTemplate.getForObject("http://EUREKA-CLIENT/hello",String.class);
    }

    public String testFallback(){
        return "error!";
    }
}
