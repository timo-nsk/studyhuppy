package com.studyhub.mindmap.application.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

	@Bean
	public JWTService jwtService() {
		return new JWTService();
	}
}
