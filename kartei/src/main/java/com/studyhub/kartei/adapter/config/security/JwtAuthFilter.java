package com.studyhub.kartei.adapter.config.security;

import com.studyhub.jwt.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

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

		// Extrahiere das Token aus der Anfrage
		String token = extractToken(request);

		if (token != null) {
			// Extrahiere den Benutzernamen und andere Claims aus dem Token
			String username = jwtService.extractUsername(token);

			// Hier kannst du direkt auf die Claims zugreifen, ohne UserDetailsService zu verwenden
			if (jwtService.validateToken(token, username)) {
				// Du kannst auch Rollen und Berechtigungen aus den Claims extrahieren
				//List<GrantedAuthority> authorities = jwtService.getAuthoritiesFromToken(token);

				// Erstelle ein Authentication-Objekt basierend auf den extrahierten Informationen
				UsernamePasswordAuthenticationToken authentication =
						new UsernamePasswordAuthenticationToken(username, null, List.of(new SimpleGrantedAuthority("ROLE_USER")));
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				// Setze die Authentifizierung in den SecurityContext
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}

		// Fahre mit dem Filter fort
		filterChain.doFilter(request, response);
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
