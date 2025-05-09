package com.studyhub.authentication.web.controller;

import com.studyhub.authentication.web.LoginRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import com.studyhub.authentication.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
public class AuthenticationController {

	private final AuthenticationService authenticationService;

	public AuthenticationController(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	@PostMapping("/login/auth")
	public ResponseEntity<Map<String, Object>> authenticateLogin(@RequestBody LoginRequest loginRequest) {

		try {
			String authToken = authenticationService.verify(loginRequest);

			Map<String, Object> response = new HashMap<>();
			response.put("validated", authToken);

			return ResponseEntity.ok(response);
		} catch (BadCredentialsException e) {
			Map<String, Object> response = new HashMap<>();
			response.put("validated", Boolean.FALSE);

			return ResponseEntity.ok(response);
		}

	}
}