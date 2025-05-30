package com.studyhub.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class AuthenticationApplication {

	private static final Logger log = LoggerFactory.getLogger(AuthenticationApplication.class);

	public static void main(String[] args) {
		log.info("WAS BUILD CORRECT");
		SpringApplication.run(AuthenticationApplication.class, args);
	}

}
