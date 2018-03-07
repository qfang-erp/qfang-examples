package com.qfang.examples.ribbonconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableCircuitBreaker
@EnableDiscoveryClient
@SpringBootApplication
public class RibbonConsumerApplication2 {

   @Bean
   @LoadBalanced
   RestTemplate restTemplate(){
      return new RestTemplate();
   }

	public static void main(String[] args) {
		SpringApplication.run(RibbonConsumerApplication2.class, args);
	}
}
