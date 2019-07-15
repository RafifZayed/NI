package com.ni.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
/**
 * 
 * @author rafifzayed
 *
 */
@SpringBootApplication
@EnableEurekaClient
public class AuthApp {

	public static void main(String[] args) {
		SpringApplication.run(AuthApp.class, args);
	}

	
}
