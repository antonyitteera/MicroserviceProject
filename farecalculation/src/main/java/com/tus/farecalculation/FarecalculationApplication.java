package com.tus.farecalculation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.client.RestTemplate;

@EnableEurekaClient
@SpringBootApplication
@EnableResourceServer
public class FarecalculationApplication {

	public static void main(String[] args) {
		SpringApplication.run(FarecalculationApplication.class, args);
		
	}
	
	@LoadBalanced
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
