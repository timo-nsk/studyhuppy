package com.studyhub.authentication.config;

import com.studyhub.jwt.JWTService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class RedirectEntryPoint implements AuthenticationEntryPoint {
	private final JWTService jwtService;

	public RedirectEntryPoint(JWTService jwtService) {
		this.jwtService = jwtService;
	}


	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
		// Leite den Benutzer zur Login-Seite des Authentifizierungsservices weiter
		String token = extractToken(request);

		//Falls der Token vorhanden ist, wird geprüft ob der Token valide ist
		if(token != null) {
			String username = jwtService.extractUsername(token);

			if (jwtService.validateToken(token, username)) {
				return;
			}
			// Der Token ist nicht valide und es wird zur Login-Seite redirected
			String redirectUrl = request.getRequestURL().toString();
			String loginUrl = "http://localhost:8084/login?redirect=" + URLEncoder.encode(redirectUrl, StandardCharsets.UTF_8);
			response.sendRedirect(loginUrl);
		} else {
			// Der Token ist nicht vorhanden und es wird zur Login-Seite redirected
			String redirectUrl = request.getRequestURL().toString();
			String loginUrl = "http://localhost:8084/login?redirect=" + URLEncoder.encode(redirectUrl, StandardCharsets.UTF_8);
			response.sendRedirect(loginUrl);
		}
	}

	private String extractToken(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("auth_token".equals(cookie.getName())) return cookie.getValue();
			}
		}
		return null;
	}
}

