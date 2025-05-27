package com.studyhub.mail.adapter.web;

import com.studyhub.mail.adapter.auth.RegistrationRequest;
import com.studyhub.mail.application.service.TemplateMailService;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailController {

	private final TemplateMailService templateMailService;

	public MailController(TemplateMailService templateMailService) {
		this.templateMailService = templateMailService;
	}

	@GetMapping("/health-ping")
	public ResponseEntity<String> healthPing() {
		return ResponseEntity.ok("Mail service is healthy");
	}

	@PostMapping("/new-user-registration")
	public ResponseEntity<String> newUserRegistration(@RequestBody RegistrationRequest registrationRequest) throws MessagingException {
		//TODO: implement sending mail
		templateMailService.prepareRegistrationConfirmationTemplating(registrationRequest);
		return ResponseEntity.ok("successfully send registration confirmation");
	}

	@PostMapping("/user-change-mail")
	public ResponseEntity<Void> sendChangedEmailInformation() {
		System.out.println("sendChangedEmailInformation ping");
		//TODO: implement
		return ResponseEntity.ok().build();
	}
}
