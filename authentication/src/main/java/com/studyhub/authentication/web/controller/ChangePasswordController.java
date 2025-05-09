package com.studyhub.authentication.web.controller;

import com.studyhub.authentication.web.ChangePasswordForm;
import jakarta.validation.Valid;
import com.studyhub.authentication.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ChangePasswordController {

	private final AccountService accountService;

	public ChangePasswordController(AccountService accountService) {
		this.accountService = accountService;
	}

	@GetMapping("/change-pass")
	public String changePassword(ChangePasswordForm changePasswordForm) {
		return "change-pass";
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
}
