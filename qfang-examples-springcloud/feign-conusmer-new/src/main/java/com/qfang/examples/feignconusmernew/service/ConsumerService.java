package com.qfang.examples.feignconusmernew.service;

import com.qfang.examples.feignconusmernew.entity.Person;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author huxianyong
 * @date 2018/3/7
 * @since 1.0
 */
@FeignClient(name = "eureka-client", fallback = ConsumerServiceFallback.class)   //通过@FeignClient注解指定服务名来绑定服务
public interface ConsumerService {

    @RequestMapping(value = "/hello", method = {RequestMethod.GET})   //绑定REAT接口
    String hello();

    @RequestMapping(value = "/hello1", method = {RequestMethod.GET})
    String hello(@RequestParam("name") String name);

    @RequestMapping(value = "/hello2", method = {RequestMethod.GET})
    Person hello(@RequestHeader("name") String name, @RequestHeader("age") Integer age);

    @RequestMapping(value = "/hello3", method = {RequestMethod.POST})
    String hello(@RequestBody Person user);

}
