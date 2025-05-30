package com.studyhub.authentication.config;

import com.studyhub.authentication.config.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Lazy;
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

	public JwtAuthFilter(@Lazy JWTService jwtService, UserDetailsService userDetailsService) {
		this.jwtService = jwtService;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
	                                HttpServletResponse response,
	                                FilterChain filterChain) throws ServletException, IOException {

		String path = request.getRequestURI();
		if (path.equals("/api/v1login") || path.equals("/api/v1/register") || path.equals("/agb") || path.equals("/api/v1/password-reset")
				|| path.equals("/api/v1/get-db-health") || path.equals("/actuator/health")) {
			filterChain.doFilter(request, response);
			return;
		}

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
