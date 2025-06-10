package com.studyhub.authentication.web.controller.api;

import com.studyhub.authentication.adapter.mail.MailRequestService;
import com.studyhub.authentication.config.JWTService;
import com.studyhub.authentication.model.AppUser;
import com.studyhub.authentication.service.EmailChangeException;
import com.studyhub.authentication.web.ChangePasswordRequest;
import com.studyhub.authentication.web.EmailChangeRequest;
import com.studyhub.authentication.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/auth/v1/")
public class ChangeAccountDetailsController {

	private final AccountService accountService;
	private final MailRequestService mailRequestService;
	private final JWTService jwtService;

	public ChangeAccountDetailsController(AccountService accountService, MailRequestService mailRequestService, JWTService jwtService) {
		this.accountService = accountService;
		this.mailRequestService = mailRequestService;
		this.jwtService = jwtService;
	}

	@PutMapping("/change-password")
	public ResponseEntity<Void> tryChangePassword(@RequestBody ChangePasswordRequest req, HttpServletRequest servletRequest) {
		if(accountService.validPassword(req.getOldPw(), req.getUserId())) {
			String username = jwtService.extractUsernameFromHeader(servletRequest);
			AppUser user = accountService.findByUsername(username);
			accountService.changePassword(req.getNewPw(), req.getUserId());
			req.setUsername(jwtService.extractUsernameFromHeader(servletRequest));
			req.setMail(user.getMail());
			req.clearPasswords();
			mailRequestService.sendChangePasswordInformation(req);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.badRequest().build();
		}
	}

	@PutMapping("/change-mail")
	public ResponseEntity<Void> changeMail(@RequestBody EmailChangeRequest req, HttpServletRequest servletRequest) {
		try {
			accountService.changeMail(req);
			req.setUsername(jwtService.extractUsernameFromHeader(servletRequest));
			mailRequestService.sendChangeMailInformation(req);
			return ResponseEntity.ok().build();
		} catch (EmailChangeException e) {
			return ResponseEntity.badRequest().build();
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
