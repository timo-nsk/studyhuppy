package com.studyhub.authentication.web.controller;

import com.studyhub.authentication.web.LoginRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import com.studyhub.authentication.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/auth/v1/")
//@CrossOrigin(origins = "https://studyhuppy.de")
public class AuthenticationController {

	private final AuthenticationService authenticationService;

	public AuthenticationController(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	@PostMapping("/login")
	public ResponseEntity<String> authenticateLogin(@RequestBody LoginRequest loginRequest) {
		try {
			String authToken = authenticationService.verify(loginRequest);
			return ResponseEntity.ok(authToken);
		} catch (BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} catch (UsernameNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
}