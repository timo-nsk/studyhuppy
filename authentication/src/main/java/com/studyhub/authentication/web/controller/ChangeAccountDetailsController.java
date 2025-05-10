package com.studyhub.authentication.web.controller;

import com.studyhub.authentication.web.ChangePasswordForm;
import com.studyhub.authentication.web.EmailChangeRequest;
import jakarta.validation.Valid;
import com.studyhub.authentication.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ChangeAccountDetailsController {

	private final AccountService accountService;

	public ChangeAccountDetailsController(AccountService accountService) {
		this.accountService = accountService;
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
		accountService.changeMail(req);
		return ResponseEntity.ok().build();
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
