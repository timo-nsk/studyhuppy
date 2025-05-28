package com.studyhub.authentication.adapter.actuator;

import com.studyhub.authentication.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HealthController {

	private final AuthenticationService authenticationService;

	public HealthController(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	@GetMapping("/get-db-health")
	public ResponseEntity<String> getDbHealth() {

		System.out.println("auth health: " + authenticationService.isUserDbHealthy());
		return ResponseEntity.ok(String.valueOf(authenticationService.isUserDbHealthy()));
	}
}
