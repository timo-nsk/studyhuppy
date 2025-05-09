package com.studyhub.track.adapter.actuator;


import com.studyhub.track.application.service.ModulService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HealthController {

	private final ModulService modulService;

	public HealthController(ModulService modulService) {
		this.modulService = modulService;
	}

	@RequestMapping("/get-db-health")
	public String getDbHealth() {

		boolean modulDbHealth = modulService.isModulDbHealthy();

		return String.valueOf(modulDbHealth);
	}
}
