package com.studyhub.mail;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.KeyGenerator;
import java.security.NoSuchAlgorithmException;

@Configuration
public class BeanConfig {

	@Bean
	public KeyGenerator keyGenerator() throws NoSuchAlgorithmException {
		return KeyGenerator.getInstance("HmacSHA256");
	}

}
