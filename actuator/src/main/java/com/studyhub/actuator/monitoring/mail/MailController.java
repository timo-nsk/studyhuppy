package com.studyhub.actuator.monitoring.mail;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MailController {

	@GetMapping("/mail-service")
	public String mailDetails() {
		return "mail-service";
	}
}
