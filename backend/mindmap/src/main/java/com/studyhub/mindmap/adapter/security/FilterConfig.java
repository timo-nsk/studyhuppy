package com.studyhub.mindmap.adapter.security;

import com.studyhub.mindmap.application.service.JWTService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<ActuatorAuthFilter> tokenHeaderFilter() {
        FilterRegistrationBean<ActuatorAuthFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new ActuatorAuthFilter(new JWTService()));
        registrationBean.addUrlPatterns("/api/mindmap/v1/get-db-health", "/actuator/health");
        registrationBean.setOrder(0);

        return registrationBean;
    }
}
