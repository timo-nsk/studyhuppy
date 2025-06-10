package com.studyhub.mail.adapter.web;

import com.studyhub.mail.adapter.auth.ChangePasswordRequest;
import com.studyhub.mail.adapter.auth.EmailChangeRequest;
import com.studyhub.mail.adapter.auth.RegistrationRequest;
import com.studyhub.mail.application.service.MailingPipe;
import com.studyhub.mail.domain.model.MailTyp;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mail/v1")
public class MailApiController {

	@Value("${application.mail.address}")
	private String appMailAddress;
	private final MailingPipe mailingPipe;

	public MailApiController(MailingPipe mailingPipe) {
        this.mailingPipe = mailingPipe;
    }

	@GetMapping("/health-ping")
	public ResponseEntity<String> healthPing() {
		return ResponseEntity.ok("Mail service is healthy");
	}

	@PostMapping("/new-user-registration")
	public ResponseEntity<Void> newUserRegistration(@RequestBody RegistrationRequest registrationRequest) throws MessagingException {
		mailingPipe
			.withContextVariable("username", registrationRequest.username())
			.withContextVariable("mail", registrationRequest.mail())
			.withTemplatePath("mail/registration-confirmation/registration-confirmation")
			.withMessage("Studyhuppy - Registrierung erfolgreich", appMailAddress, registrationRequest.mail())
			.sendMail()
			.saveMailGesendetEvent(MailTyp.REGISTRATION_CONFIRMATION, true);

		return ResponseEntity.ok().build();
	}

	@PostMapping("/user-change-mail")
	public ResponseEntity<Void> sendChangedEmailInformation(@RequestBody EmailChangeRequest emailChangeRequest) {
		mailingPipe
				.withContextVariable("newMailAddress", emailChangeRequest.getNewMail())
				.withContextVariable("username", emailChangeRequest.getUsername())
				.withTemplatePath("mail/email-change/email-change")
				.withMessage("Studyhuppy - E-Mail-Addresse geändert", appMailAddress, emailChangeRequest.getNewMail())
				.sendMail()
				.saveMailGesendetEvent(MailTyp.USER_DATA_CHANGE, true);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/user-change-password")
	public ResponseEntity<Void> sendChangedPasswordInformation(@RequestBody ChangePasswordRequest passwordChangeRequest) {
		mailingPipe
				.withContextVariable("username", passwordChangeRequest.getUsername())
				.withTemplatePath("mail/password-change/password-change")
				.withMessage("Studyhuppy - Passwort geändert", appMailAddress, passwordChangeRequest.getMail())
				.sendMail()
				.saveMailGesendetEvent(MailTyp.USER_DATA_CHANGE, true);
		return ResponseEntity.ok().build();
	}
}