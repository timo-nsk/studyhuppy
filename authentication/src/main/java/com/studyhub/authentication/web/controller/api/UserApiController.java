package com.studyhub.authentication.web.controller.api;

import com.studyhub.authentication.model.AppUser;
import com.studyhub.authentication.model.UserDto;
import com.studyhub.authentication.model.UserMapper;
import com.studyhub.authentication.service.AccountService;
import com.studyhub.authentication.web.SetNotificationSubscriptionRequest;
import com.studyhub.authentication.config.JWTService;
import com.studyhub.authentication.web.controller.ProfilbildRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth/v1/")
public class UserApiController {
	private final AccountService accountService;
	private final JWTService jwtService;

	public UserApiController(AccountService accountService, JWTService jwtService) {
		this.accountService = accountService;
		this.jwtService = jwtService;
	}


	@PostMapping("get-semester")
	public Integer getSemesterOfUser(@RequestBody String username) {
		return accountService.findSemesterByUsername(username);
	}

	@GetMapping("get-users-notification")
	public Map<String, String> getUsersWithNotificationOn() {
		return accountService.getUsersWithNotificationOn();
	}

	@GetMapping("/get-user-data")
	public ResponseEntity<UserDto> getUserData(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			String authToken = jwtService.extractTokenFromHeader(authHeader);
			String username = jwtService.extractUsername(authToken);
			AppUser user = accountService.findByUsername(username);
			System.out.println(user);

			if (user != null) return ResponseEntity.ok(UserMapper.toDto(user));
			else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}

	@PutMapping("/update-notification-subscription")
	public ResponseEntity<Void> editNotification(@RequestBody SetNotificationSubscriptionRequest payload, HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		String token = jwtService.extractTokenFromHeader(header);
		String username = jwtService.extractUsername(token);
		accountService.editNotificationSubscription(payload.activate(), username);
		return ResponseEntity.status(HttpStatus.ACCEPTED).build();
	}

	@DeleteMapping("/delete-account")
	public ResponseEntity<Void> deleteAccount(@RequestBody String userId) {

		int maxTries = 3;

		for (int i = 1; i <= maxTries; i++) {
			boolean success = accountService.deleteAccount(UUID.fromString(userId), i);
			if(success) {
				return ResponseEntity.noContent().build();
			} else if(i == maxTries) {
				return ResponseEntity.internalServerError().build();
			}
		}
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/profilbild")
	public ResponseEntity<Void> setProfilBuild(@RequestBody ProfilbildRequest payload, HttpServletRequest request) {
		accountService.saveProfilbild(payload, request);

		return ResponseEntity.status(HttpStatus.ACCEPTED).build();
	}

	@GetMapping("/profilbild/{filename}")
	public ResponseEntity<Resource> getProfilbild(@PathVariable String filename) throws IOException {
		Path filePath = Paths.get("authentication/user-data/uploads").resolve(filename);
		Resource file = new UrlResource(filePath.toUri());

		if (file.exists() && file.isReadable()) {
			return ResponseEntity.ok()
					.contentType(MediaType.valueOf(Files.probeContentType(filePath)))
					.body(file);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
