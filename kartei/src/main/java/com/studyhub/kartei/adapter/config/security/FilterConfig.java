package com.studyhub.kartei.adapter.config.security;

import com.studyhub.jwt.JWTService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<ActuatorAuthFilter> tokenHeaderFilter() {
        FilterRegistrationBean<ActuatorAuthFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new ActuatorAuthFilter(new JWTService()));
        registrationBean.addUrlPatterns("/api/get-db-health", "/actuator/health");
        registrationBean.setOrder(0);

        return registrationBean;
    }
}
