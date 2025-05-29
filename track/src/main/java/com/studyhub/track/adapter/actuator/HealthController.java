package com.studyhub.track.adapter.actuator;

import com.studyhub.track.application.service.ModulService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class HealthController {

	private final ModulService modulService;

    public HealthController(ModulService modulService) {
        this.modulService = modulService;
    }

    @GetMapping("/get-db-health")
	public ResponseEntity<String> getDbHealth() {
        return ResponseEntity.ok(String.valueOf(modulService.isModulDbHealthy()));
	}
}
