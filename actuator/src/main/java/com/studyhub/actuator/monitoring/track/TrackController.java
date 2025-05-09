package com.studyhub.actuator.monitoring.track;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TrackController {
	@GetMapping("/track-service")
	public String trackDetails() {
		return "track-service";
	}
}
