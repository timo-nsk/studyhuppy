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

	@PostMapping("/change-pass")
	public String tryChangePassword(@Valid ChangePasswordForm changePasswordForm,
	                                BindingResult bindingResult,
	                                RedirectAttributes redirectAttributes,
	                                @CookieValue("auth_token") String token) {

		if (bindingResult.hasErrors()) return "change-pass";


		redirectAttributes.addFlashAttribute("changePasswordForm", changePasswordForm);

		accountService.changePassword(changePasswordForm.newPassword(), token);

		return "redirect:/profil";
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
