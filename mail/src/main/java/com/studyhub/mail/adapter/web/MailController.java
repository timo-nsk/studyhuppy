package com.studyhub.mail.adapter.web;

import com.studyhub.mail.adapter.auth.EmailChangeRequest;
import com.studyhub.mail.adapter.auth.RegistrationRequest;
import com.studyhub.mail.application.service.MailGesendetEventService;
import com.studyhub.mail.application.service.TemplateMailService;
import com.studyhub.mail.domain.model.MailTyp;
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
	private MailGesendetEventService mailGesendetEventService;

	public MailController(TemplateMailService templateMailService, MailGesendetEventService mailGesendetEventService) {
		this.templateMailService = templateMailService;
        this.mailGesendetEventService = mailGesendetEventService;
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
			mailGesendetEventService.prepareSavingEvent(MailTyp.USER_DATA_CHANGE, true);
			log.info("Sent email change confirmation");
			return ResponseEntity.ok().build();
		} catch(MailException | MessagingException e) {
			log.error("Failed to send email change confirmation", e);
			mailGesendetEventService.prepareSavingEvent(MailTyp.USER_DATA_CHANGE, false);
			return ResponseEntity.internalServerError().build();
		}
	}
}
