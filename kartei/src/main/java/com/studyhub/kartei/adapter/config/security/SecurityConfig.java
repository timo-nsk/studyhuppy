package com.studyhub.kartei.adapter.config.security;

import com.studyhub.kartei.service.application.JWTService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private JwtAuthFilter jwtAuthenticationFilter;
	private final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

	public SecurityConfig(JwtAuthFilter jwtAuthenticationFilter) {
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// TODO: csrf muss im frontend gemacht werden, hier werden keine Formulre templated
		return http
			.csrf().disable() //muss disabled sein
			.cors(Customizer.withDefaults())
			//.requiresChannel().anyRequest().requiresSecure().and()
			.authorizeHttpRequests(auth -> auth
					.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
					.requestMatchers("/api/v1/get-db-health", "/actuator/health").permitAll().anyRequest().authenticated())
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
			.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		log.info("Configuring CorsConfigurationSource...");

		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(List.of(
				"http://localhost:4200",
				"http://127.0.0.1:4200",
				"http://127.0.0.1:8080",
				"http://49.12.242.124:8080",
				"http://49.12.242.124:4200"
		));
		config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		config.setAllowedHeaders(List.of("*"));
		config.setExposedHeaders(List.of("Authorization"));
		config.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);

		log.info("CorsConfigurationSource configured.");
		return source;
	}

	@Bean
	public JWTService jwtService() {
		return new JWTService();
	}
}