package com.studyhub.authentication.web;

import com.studyhub.jwt.JWTService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.studyhub.authentication.db.AppUserRepository;
import com.studyhub.authentication.model.AppUser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserPasswordValidator implements ConstraintValidator<CorrectPassword, String> {


	private final HttpServletRequest request;
	private final AppUserRepository appUserRepository;
	private final JWTService jwtService;


	public UserPasswordValidator(HttpServletRequest request, AppUserRepository appUserRepository, JWTService jwtService) {
		this.request = request;
		this.appUserRepository = appUserRepository;
		this.jwtService = jwtService;
	}

	@Override
	public boolean isValid(String sendPassword, ConstraintValidatorContext context) {
		String authToken = getAuthToken(request);

		System.out.println("Auth token: " + authToken);

		if (authToken == null) return false;

		String username = jwtService.extractUsername(authToken);
		AppUser appUser = appUserRepository.findByUsername(username);

		System.out.println("App user: " + appUser);

		if (appUser == null) return false;

		String password = appUser.getPassword();
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

		boolean matches = bCryptPasswordEncoder.matches(sendPassword, password);

		System.out.println("Password matches: " + matches);

		return matches;
	}

	public String getAuthToken(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();

		for (Cookie cookie : cookies) {
			if ("auth_token".equals(cookie.getName())) return cookie.getValue();

		}
		return null;
	}
}
