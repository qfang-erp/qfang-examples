package com.qfang.examples.feignconusmernew.controller;


import com.qfang.examples.feignconusmernew.entity.Person;
import com.qfang.examples.feignconusmernew.service.ConsumerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class ConsumerController {

    @Resource
    ConsumerService consumerService;

    @GetMapping(value = "/feign-consumer")
    public String helloConsumer() {
        return consumerService.hello();
    }

    @GetMapping(value = "/feign-consumer2")
    public String helloConsumer2() {
        StringBuilder sb = new StringBuilder();

        sb.append(consumerService.hello()).append("\n");
        sb.append(consumerService.hello("caogen")).append("\n");
        sb.append(consumerService.hello("caogen", 18)).append("\n");
        sb.append(consumerService.hello(new Person("caogen", 18))).append("\n");

        return sb.toString();
    }

}
