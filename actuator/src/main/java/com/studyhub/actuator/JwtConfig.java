package com.studyhub.actuator;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Bean
    public JWTService jwtService() {
        return new JWTService();
    }
}
