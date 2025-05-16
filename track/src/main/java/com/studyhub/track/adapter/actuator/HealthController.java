package com.studyhub.track.adapter.actuator;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HealthController {


	@RequestMapping("/get-db-health")
	public String getDbHealth() {

		boolean modulDbHealth = true;

		return String.valueOf(modulDbHealth);
	}
}
