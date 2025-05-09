package com.studyhub.authentication.web.controller;

import com.studyhub.authentication.web.LoginRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import com.studyhub.authentication.service.AuthenticationService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthenticationController {

	private final AuthenticationService authenticationService;

	public AuthenticationController(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	@GetMapping("/login")
	public ModelAndView login(@RequestParam(required = false) String redirect,
	                          LoginRequest loginRequest) {
		ModelAndView modelAndView = new ModelAndView("login");
		modelAndView.addObject("loginRequest", loginRequest);
		modelAndView.addObject("redirect", redirect);
		return modelAndView;
	}

	@PostMapping("/login/auth")
	public  String authenticateLogin(@Valid LoginRequest loginRequest,
	                                 BindingResult bindingResult,
	                                 RedirectAttributes redirectAttributes,
	                                 HttpServletResponse resp,
	                                 String redirect) {

		if (bindingResult.hasErrors()) return "login";

		redirectAttributes.addFlashAttribute("loginRequest", loginRequest);

		String token =  authenticationService.verify(loginRequest);
		authenticationService.createAuthTokenCookie(resp, token);

		if(redirect != null && !redirect.isEmpty()) return "redirect:" + redirect;
		return "redirect:/home";
	}
}