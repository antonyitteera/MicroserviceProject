package com.tus.usermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

//@EnableEurekaClient
@SpringBootApplication
//@EnableFeignClients
@EnableResourceServer
public class EasyfareApplication {

	public static void main(String[] args) {
		SpringApplication.run(EasyfareApplication.class, args);
	}

}
