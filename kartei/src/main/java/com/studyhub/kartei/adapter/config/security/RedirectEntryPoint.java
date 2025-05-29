package com.studyhub.kartei.adapter.config.security;

import com.studyhub.kartei.service.application.JWTService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class RedirectEntryPoint implements AuthenticationEntryPoint {
	private final JWTService jwtService;

	public RedirectEntryPoint(@Lazy JWTService jwtService) {
		this.jwtService = jwtService;
	}

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
		// Leite den Benutzer zur Login-Seite des Authentifizierungsservices weiter
		String token = extractToken(request);
		System.out.println("redirectentry: " + token);
		String username = jwtService.extractUsername(token);

		if (jwtService.validateToken(token, username)) {
			return;
		}

		System.out.println("Token in commence: " + token);


		System.out.println("could not authenticate or something");
		String redirectUrl = request.getRequestURL().toString();
		String loginUrl = "http://localhost:8084/login?redirect=" + URLEncoder.encode(redirectUrl, StandardCharsets.UTF_8.name());



		response.sendRedirect(loginUrl);
	}

	private String extractToken(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("auth_token".equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}
}

