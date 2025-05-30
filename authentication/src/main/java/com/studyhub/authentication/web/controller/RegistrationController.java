package com.studyhub.authentication.web.controller;

import com.studyhub.authentication.service.RegistrationService;
import com.studyhub.authentication.web.RegisterRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/v1/")
public class RegistrationController {

	private final RegistrationService registrationService;

	public RegistrationController(RegistrationService registrationService) {
		this.registrationService = registrationService;
	}

	@PostMapping("/register")
	public ResponseEntity<Map<String, Object>> registerNewUser(@RequestBody RegisterRequest registerRequest) {
		boolean success = registrationService.register(registerRequest.toAppUser());

		Map<String, Object> response = new HashMap<>();
		response.put("success", success);

		if (success) return ResponseEntity.ok(response);
		else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}
}