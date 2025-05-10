package com.studyhub.authentication.web.controller;

import com.studyhub.authentication.web.ChangePasswordForm;
import jakarta.validation.Valid;
import com.studyhub.authentication.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ChangePasswordController {

	private final AccountService accountService;

	public ChangePasswordController(AccountService accountService) {
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
