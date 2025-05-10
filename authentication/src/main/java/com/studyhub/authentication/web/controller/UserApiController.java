package com.studyhub.authentication.web.controller;

import com.studyhub.authentication.model.AppUser;
import com.studyhub.authentication.model.UserDto;
import com.studyhub.authentication.model.UserMapper;
import com.studyhub.authentication.service.AccountService;
import com.studyhub.jwt.JWTService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
public class UserApiController {
	private final AccountService accountService;
	private final JWTService jwtService;

	public UserApiController(AccountService accountService, JWTService jwtService) {
		this.accountService = accountService;
		this.jwtService = jwtService;
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

	@GetMapping("/get-user-data")
	public ResponseEntity<UserDto> getUserData(HttpServletRequest request) {
		System.out.println("ping");
		String authHeader = request.getHeader("Authorization");
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			String authToken = jwtService.extractTokenFromHeader(authHeader);
			String username = jwtService.extractUsername(authToken);
			AppUser user = accountService.findByUsername(username);

			if (user != null) return ResponseEntity.ok(UserMapper.toDto(user));
			else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}


}
