package com.studyhub.authentication.config;

import com.studyhub.jwt.JWTService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.studyhub.authentication.service.AppUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final JwtAuthFilter jwtAuthFilter;
	private final AppUserDetailsService userDetailsService;

	public SecurityConfig(JwtAuthFilter jwtAuthFilter, AppUserDetailsService userDetailsService) {
		this.jwtAuthFilter = jwtAuthFilter;
		this.userDetailsService = userDetailsService;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(authorizeRequests -> authorizeRequests
						.requestMatchers(
								"/login",
								"/login/auth",
								"/register",
								"/agb",
								"/register/new-user",
								"/api/get-db-health",
								"/api/v1/get-semester",
								"/api/v1/get-users-notification",
								"/api/v1/get-user-data",
								"/actuator/**",
								"/fragments",
								"/styles/**",
								"/css/**").permitAll()
						.anyRequest().authenticated())
				.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
				.logout(logout -> logout
						.logoutUrl("/logout")
						.logoutSuccessUrl("/login")
						.deleteCookies("auth_token")
						.deleteCookies("JSESSIONID")
						.invalidateHttpSession(true)
						.clearAuthentication(true))
				.exceptionHandling(c -> c.authenticationEntryPoint(new RedirectEntryPoint(new JWTService())))
				.build();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(new BCryptPasswordEncoder(10));
		provider.setUserDetailsService(userDetailsService);
		return provider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		return new AccessDeniedHandlerImpl() {
			@Override
			public void handle(HttpServletRequest request, HttpServletResponse response,
			                   AccessDeniedException accessDeniedException) throws IOException {
				response.sendRedirect("/login");
			}
		};
	}
	@Bean
	public JWTService jwtService() {
		return new JWTService();
	}
}
