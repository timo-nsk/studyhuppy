package com.studyhub.actuator.monitoring.kartei;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class KarteiController {
	@GetMapping("/kartei-service")
	public String karteiDetails() {
		return "kartei-service";
	}
}
