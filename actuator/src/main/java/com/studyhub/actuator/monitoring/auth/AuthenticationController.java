package com.studyhub.actuator.monitoring.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticationController {
	@GetMapping("/authentication-service")
	public String authDetails() {
		return "authentication-service";
	}
}
