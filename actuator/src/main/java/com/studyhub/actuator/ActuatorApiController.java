package com.studyhub.actuator;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/v1")
public class ActuatorApiController {

	private final SystemHealthService systemHealthService;

	public ActuatorApiController(SystemHealthService systemHealthService) {
		this.systemHealthService = systemHealthService;
	}

	@GetMapping("/get-system-health")
	public ResponseEntity<List<SystemHealth>> getSystemHealth() {
		return ResponseEntity.ok(systemHealthService.getSystemHealth());
	}
}
