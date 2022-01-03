package com.example.microservices.authservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class AuthServiceApplication {

	public static void main(final String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}

}
