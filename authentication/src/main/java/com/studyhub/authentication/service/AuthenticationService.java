package com.studyhub.authentication.service;

import com.studyhub.jwt.JWTService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import com.studyhub.authentication.db.AppUserRepository;
import com.studyhub.authentication.web.LoginRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

	@Value("${jwt.role.admin.username}")
	private String adminUsername;

	private AppUserRepository appUserRepository;
	private AuthenticationManager authenticationManager;
	private JWTService jwtService;
	private Logger log = LoggerFactory.getLogger(AuthenticationService.class);

	public AuthenticationService(AppUserRepository appUserRepository, AuthenticationManager authenticationManager, JWTService jwtService) {
		this.appUserRepository = appUserRepository;
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
	}

	public String verify(LoginRequest loginRequest) throws BadCredentialsException {
		if(appUserRepository.findByUsername(loginRequest.username()) == null) {
			throw new UsernameNotFoundException(loginRequest.username() + " not found");
		}

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));

		if (authentication.isAuthenticated()) {
			log.info("User authenticated successfully (%s)".formatted(authentication));
			return jwtService.generateToken(loginRequest.username());
		} else {
			log.error("User authenticated not successfully (%s)".formatted(authentication));
			return "fail";
		}
	}

	public void createAuthTokenCookie(HttpServletResponse response, String token) {
		Cookie cookie = new Cookie("auth_token", token);
		cookie.setHttpOnly(true);
		cookie.setSecure(true);
		cookie.setPath("/");
		cookie.setMaxAge(60 * 60 * 24); // 24h
		response.addCookie(cookie);
	}

	public boolean isUserDbHealthy() {
		Integer result = appUserRepository.isUserDbHealthy();
		return result != null;
	}
}
