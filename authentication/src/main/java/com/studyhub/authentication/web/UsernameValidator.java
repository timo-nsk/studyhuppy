package com.studyhub.authentication.web;

import com.studyhub.authentication.db.AppUserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<IsAvailable, String> {

	private final HttpServletRequest request;
	private final AppUserRepository appUserRepository;

	public UsernameValidator(HttpServletRequest request, AppUserRepository appUserRepository) {
		this.request = request;
		this.appUserRepository = appUserRepository;
	}

	@Override
	public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
		String username = request.getParameter("username");
		if(username.isEmpty()) return true;
		return appUserRepository.findByUsername(username) == null;
	}

	public String getAuthToken(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();

		for (Cookie cookie : cookies) {
			if ("auth_token".equals(cookie.getName())) return cookie.getValue();

		}
		return null;
	}
}
