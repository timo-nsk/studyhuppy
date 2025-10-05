package com.studyhub.mail.adapter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.context.Context;

@Configuration
public class BeanConfig {

    @Bean
    public Context provideContext() {
        return new Context();
    }
}
