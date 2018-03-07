package com.qfang.examples.turbinemanage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

@EnableTurbine
@EnableDiscoveryClient
@SpringBootApplication
public class TurbineManageApplication {

	public static void main(String[] args) {
		SpringApplication.run(TurbineManageApplication.class, args);
	}
}
