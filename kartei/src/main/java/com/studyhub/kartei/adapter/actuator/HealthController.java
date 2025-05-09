package com.studyhub.kartei.adapter.actuator;

import com.studyhub.kartei.service.application.StapelService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HealthController {

	private final StapelService stapelService;

	public HealthController(StapelService stapelService) {
		this.stapelService = stapelService;
	}

	@GetMapping("/get-db-health")
	public String getDbHealth() {

		boolean modulDbHealth = stapelService.isStapelDbHealthy();

		return String.valueOf(modulDbHealth);
	}
}
