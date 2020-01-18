package com.jschirm.playerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication
public class PlayerApplication {//extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(PlayerApplication.class, args);
	}

}
