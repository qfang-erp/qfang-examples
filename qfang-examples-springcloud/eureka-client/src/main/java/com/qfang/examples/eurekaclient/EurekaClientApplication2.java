package com.qfang.examples.eurekaclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class EurekaClientApplication2 {

	public static void main(String[] args) {
		SpringApplication.run(EurekaClientApplication2.class, args);
	}
}
