package com.studyhub.kartei.adapter.config.security;

import com.studyhub.jwt.JWTService;
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


	private RedirectEntryPoint redirectEntryPoint;

	public SecurityConfig(JwtAuthFilter jwtAuthenticationFilter, RedirectEntryPoint redirectEntryPoint) {
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
		this.redirectEntryPoint = redirectEntryPoint;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				// TODO: muss aus sein, weil sonst die keine POST requests als 403. fixen
				.csrf().disable()
				.cors(Customizer.withDefaults())
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/api/**").permitAll()
						.anyRequest().authenticated()
				)
				.exceptionHandling(exceptionHandling -> exceptionHandling
						.authenticationEntryPoint(redirectEntryPoint)  // Setze den Custom Authentication Entry Point
				)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public JWTService jwtService() {
		return new JWTService();
	}
}

