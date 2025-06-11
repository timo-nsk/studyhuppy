package com.studyhub.authentication.config;

import com.studyhub.authentication.config.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {


	private JWTService jwtService;
	private UserDetailsService userDetailsService;
	private Logger log = LoggerFactory.getLogger(JwtAuthFilter.class);

	public JwtAuthFilter(@Lazy JWTService jwtService, UserDetailsService userDetailsService) {
		this.jwtService = jwtService;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
	                                HttpServletResponse response,
	                                FilterChain filterChain) throws ServletException, IOException {
		log.info("Try JwtAuthFilter ...");
		String path = request.getRequestURI();
		if (path.equals("/api/auth/v1/login") || path.equals("/api/auth/v1/register") || path.equals("/agb") || path.equals("/api/auth/v1/password-reset")
				|| path.equals("/api/v1/get-db-health") || path.equals("/actuator/health") || path.equals("/api/auth/v1/kontakt-message")) {
			log.info("No need to filter request to path: " + path);
			filterChain.doFilter(request, response);
			return;
		}

		log.info("Check Header...");

		String header = request.getHeader("Authorization");
		String token = jwtService.extractTokenFromHeader(header);

		if (token != null) {
			String username = jwtService.extractUsername(token);
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);

			if(jwtService.validateToken(token, userDetails.getUsername())) {
				UsernamePasswordAuthenticationToken authentication =
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}

		filterChain.doFilter(request, response);
	}
}
