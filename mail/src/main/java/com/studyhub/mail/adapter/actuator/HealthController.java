package com.studyhub.mail.adapter.actuator;

import com.studyhub.mail.application.service.MailGesendetEventService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HealthController {

	private final MailGesendetEventService mailGesendetEventService;

	public HealthController(MailGesendetEventService mailGesendetEventService) {
		this.mailGesendetEventService = mailGesendetEventService;
	}

	@RequestMapping("/get-db-health")
	public String getDbHealth() {
		boolean mailDbHealth = mailGesendetEventService.isMailDbHealthy();
		return String.valueOf(mailDbHealth);
	}
}
