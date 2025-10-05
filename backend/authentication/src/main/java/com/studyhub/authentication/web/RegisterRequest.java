package com.studyhub.authentication.web;

import jakarta.validation.constraints.*;
import com.studyhub.authentication.model.AppUser;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

public record RegisterRequest(
		String mail,
		String username,
		String password,
		Boolean notificationSubscription,
		Boolean acceptedAgb,
		Integer semester
) {
	public AppUser toAppUser() {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
		String encodedPassword = encoder.encode(password);
		return new AppUser(null, UUID.randomUUID(), mail, username, encodedPassword, notificationSubscription != null && notificationSubscription, acceptedAgb, semester, "none");
	}
}