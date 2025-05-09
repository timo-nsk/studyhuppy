package com.studyhub.authentication.adapter.actuator;

import com.studyhub.authentication.service.AuthenticationService;
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
	public String getDbHealth() {
		boolean userDbHealth = authenticationService.isUserDbHealthy();
		return String.valueOf(userDbHealth);
	}
}
