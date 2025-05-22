package com.studyhub.authentication.web.controller;

import com.studyhub.authentication.adapter.mail.MailRequestService;
import com.studyhub.authentication.web.ChangePasswordForm;
import com.studyhub.authentication.web.EmailChangeRequest;
import com.studyhub.authentication.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.concurrent.atomic.AtomicBoolean;

@Controller
public class ChangeAccountDetailsController {

	private final AccountService accountService;
	private final MailRequestService mailRequestService;

	public ChangeAccountDetailsController(AccountService accountService, MailRequestService mailRequestService) {
		this.accountService = accountService;
		this.mailRequestService = mailRequestService;
	}

	@PutMapping("/change-password")
	public ResponseEntity<Void> tryChangePassword(@RequestBody ChangePasswordForm req) {
		System.out.println(req);
		if(accountService.validPassword(req.oldPw(), req.userId())) {
			accountService.changePassword(req.newPw(), req.userId());
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.badRequest().build();
		}
	}

	@PutMapping("/change-mail")
	public ResponseEntity<Void> changeMail(@RequestBody EmailChangeRequest req) {
		int res = accountService.changeMail(req);
		AtomicBoolean success = new AtomicBoolean(false);
		if(res == 1) {
			mailRequestService.sendChangeMailInformation(req).doOnSuccess(mail -> {
				System.out.println("sent request to mail service");
				success.set(true);
			}).doOnError(e -> {
				System.out.println("error in mail service: " + e.getMessage());
				success.set(false);
			}).subscribe();
		}

		if(success.get()) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.internalServerError().build();
		}
	}

	@PostMapping("/password-reset")
	public ResponseEntity<Void> passwordReset(@RequestBody String email) {
		if(accountService.emailExists(email)) {
			//TODO: mail service kontaktieren und zurücksetz mail abschicken
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
