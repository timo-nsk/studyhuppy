package com.studyhub.authentication.web.controller;

import com.studyhub.authentication.service.AuthenticationService;
import com.studyhub.authentication.web.LoginRequest;
import com.studyhub.authentication.web.NewUserRegistrationException;
import com.studyhub.authentication.web.RegisterForm;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import com.studyhub.authentication.service.RegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegistrationController {

	private final RegistrationService registrationService;
	private final AuthenticationService authenticationService;

	public RegistrationController(RegistrationService registrationService, AuthenticationService authenticationService) {
		this.registrationService = registrationService;
		this.authenticationService = authenticationService;
	}

	@GetMapping("/register")
	public String register(RegisterForm registerForm) {
		return "register";
	}

	@PostMapping("/register/new-user")
	public String registerNewUser(@Valid RegisterForm registerForm,
	                              BindingResult bindingResult,
	                              RedirectAttributes redirectAttributes,
	                              HttpServletResponse response) {

		if (bindingResult.hasErrors()) return "register";

		redirectAttributes.addFlashAttribute("registerForm", registerForm);

		boolean success = registrationService.register(registerForm.toAppUser());

		if (success) {
			String token = authenticationService.verify(new LoginRequest(registerForm.username(), registerForm.password()));
			authenticationService.createAuthTokenCookie(response, token);
			return "/getting-started";
		} else {
			throw new NewUserRegistrationException("could not register new user");
		}
	}

	@GetMapping("/agb")
	public String agb() {
		return "agb";
	}
}