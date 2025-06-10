package com.studyhub.mail.adapter.web;

import com.studyhub.mail.adapter.auth.EmailChangeRequest;
import com.studyhub.mail.adapter.auth.RegistrationRequest;
import com.studyhub.mail.application.service.TemplateMailService;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mail/v1")
public class MailController {

	private final Logger log = LoggerFactory.getLogger(MailController.class);
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
	public ResponseEntity<Void> sendChangedEmailInformation(@RequestBody EmailChangeRequest emailChangeRequest) {
		try {
			templateMailService.prepareEmailChangeConfirmationTemplate(emailChangeRequest);
			log.info("Sent email change confirmation");
			return ResponseEntity.ok().build();
		} catch(MailException | MessagingException e) {
			log.error("Failed to send email change confirmation", e);
			return ResponseEntity.internalServerError().build();
		}
	}
}
