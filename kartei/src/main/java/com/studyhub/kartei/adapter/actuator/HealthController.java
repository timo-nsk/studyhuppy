package com.studyhub.kartei.adapter.actuator;

import com.studyhub.kartei.service.application.StapelService;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<String> getDbHealth() {
		return ResponseEntity.ok(String.valueOf(stapelService.isStapelDbHealthy()));
	}
}
