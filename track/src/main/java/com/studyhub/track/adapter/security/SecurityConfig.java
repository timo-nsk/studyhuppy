package com.studyhub.track.adapter.security;

import com.studyhub.jwt.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private JwtAuthFilter jwtAuthenticationFilter;
	private RedirectEntryPoint redirectEntryPoint;

	@Autowired
	public SecurityConfig(JwtAuthFilter jwtAuthenticationFilter, RedirectEntryPoint redirectEntryPoint) {
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
		this.redirectEntryPoint = redirectEntryPoint;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
		requestHandler.setCsrfRequestAttributeName(null);

		http
				// TODO: muss aus sein, weil sonst die keine POST requests als 403. fixen
				.csrf().disable()
				.authorizeHttpRequests(auth -> auth
						//TODO: nur das System soll darauf zugang haben -> Api Key
						.requestMatchers(
								"/api/**",
								"/statistics/api/stats",
								"/actuator/**",
								"/create-semester").permitAll()
						.anyRequest().authenticated())
				//.csrf(csrf -> csrf
				//		.csrfTokenRequestHandler(requestHandler)
				//		.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
				.exceptionHandling(exceptionHandling -> exceptionHandling
						.authenticationEntryPoint(redirectEntryPoint)
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

