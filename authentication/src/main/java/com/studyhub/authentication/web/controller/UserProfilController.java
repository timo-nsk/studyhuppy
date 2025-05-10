package com.studyhub.authentication.web.controller;

import com.studyhub.authentication.model.AppUser;
import com.studyhub.authentication.service.AccountDeletionException;
import com.studyhub.authentication.service.AccountService;
import com.studyhub.authentication.web.SetNotificationSubscriptionRequest;
import com.studyhub.jwt.JWTService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserProfilController {

	private final AccountService accountService;
	private final JWTService jwtService;

	public UserProfilController(AccountService accountService, JWTService jwtService) {
		this.accountService = accountService;
		this.jwtService = jwtService;
	}

	@GetMapping("/profil")
	public String profil(@CookieValue("auth_token") String token,
	                     Model model) {
		String username = jwtService.extractUsername(token);
		model.addAttribute("user", accountService.findByUsername(username));
		return "profil";
	}


	@GetMapping("/delete-account")
	public String deleteAccount() {
		return "delete-account";
	}

	@PostMapping("/delete-account")
	public String finalyDeleteAccount(@CookieValue("auth_token") String token) {
		String username = jwtService.extractUsername(token);
		int maxTries = 3;

		for (int i = 1; i <= maxTries; i++) {
			boolean success = accountService.deleteAccount(username, i);
			if(success) {
				return "redirect:/logout";
			} else if(i == maxTries) {
				throw new AccountDeletionException("Account deletion failed");
			}
		}
		return "redirect:/logout";
	}

	@GetMapping("/get-notification-subscription")
	public @ResponseBody String getNotificationSubscription(@CookieValue("auth_token") String token) {
		String username = jwtService.extractUsername(token);
		return String.valueOf(accountService.getNotificationSubscription(username));
	}
}