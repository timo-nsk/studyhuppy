package com.studyhub.kartei.adapter.config.security;

import com.studyhub.kartei.service.application.JWTService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private JwtAuthFilter jwtAuthenticationFilter;

	public SecurityConfig(JwtAuthFilter jwtAuthenticationFilter) {
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// TODO: csrf muss im frontend gemacht werden, hier werden keine Formulre templated
		http
			.csrf().disable() //muss disabled sein
			.cors(Customizer.withDefaults())
			.authorizeHttpRequests(auth -> auth.requestMatchers("/api/get-db-health", "/actuator/health").permitAll().anyRequest().authenticated())
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	public JWTService jwtService() {
		return new JWTService();
	}
}