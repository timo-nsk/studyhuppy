package com.studyhub.authentication.web.controller;

import com.studyhub.authentication.service.AccountDeletionException;
import com.studyhub.authentication.web.LoginRequest;
import com.studyhub.authentication.web.NewUserRegistrationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(NewUserRegistrationException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ModelAndView newUserRegistrationExceptionHandler(NewUserRegistrationException e) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("error");
		System.out.println("exception handler called");
		return modelAndView;
	}

	@ExceptionHandler(BadCredentialsException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ModelAndView badCredentialsExceptionHandler(BadCredentialsException e) {
		ModelAndView modelAndView = new ModelAndView();
		LoginRequest loginRequest = new LoginRequest("", "");
		modelAndView.addObject("loginRequest", loginRequest);
		modelAndView.setViewName("login");
		modelAndView.addObject("badCredentials", "Username oder Passwort sind falsch");
		return modelAndView;
	}

	@ExceptionHandler(AccountDeletionException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ModelAndView accountDeletionExceptionHandler(AccountDeletionException e) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("exceptionMessage", e.getMessage());
		modelAndView.setViewName("error");
		return modelAndView;
	}
}