package com.studyhub.authentication.web.controller;

import com.studyhub.authentication.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
public class UserApiController {
	private final AccountService accountService;

	public UserApiController(AccountService accountService) {
		this.accountService = accountService;
	}


	@PostMapping("get-semester")
	public Integer getSemesterOfUser(@RequestBody String username) {
		System.out.println("ping semester get");
		return accountService.findSemesterByUsername(username);
	}

	@GetMapping("get-users-notification")
	public Map<String, String> getUsersWithNotificationOn() {
		return accountService.getUsersWithNotificationOn();
	}


}
