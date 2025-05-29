package com.studyhub.authentication.service;

import com.studyhub.jwt.JWTService;
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

import java.util.List;

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
			if(authentication.getName().equals(adminUsername)) {
				return jwtService.generateToken(loginRequest.username(), List.of("ROLE_ADMIN"));
			} else {
				return jwtService.generateToken(loginRequest.username(), List.of("ROLE_USER"));
			}

		} else {
			log.error("User authenticated not successfully (%s)".formatted(authentication));
			return "fail";
		}
	}

	public boolean isUserDbHealthy() {
		Integer result = appUserRepository.isUserDbHealthy();
		return result != null;
	}
}
