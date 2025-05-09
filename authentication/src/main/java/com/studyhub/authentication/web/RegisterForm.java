package com.studyhub.authentication.web;

import jakarta.validation.constraints.*;
import com.studyhub.authentication.model.AppUser;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

public record RegisterForm(
		@Email(message = "Keine gültige E-Mail-Adresse")
		@NotBlank(message = "Keine E-Mail-Adresse eingegeben")
		String mail,

		@NotBlank(message = "Kein Username eingegeben")
		@IsAvailable
		String username,

		@NotBlank(message = "Kein Passwort eingegeben")
		@Length(message = "Passwort muss 8 Zeichen lang sein", min = 8)
		String password,

		Boolean notificationSubscription,
		@NotNull(message = "AGB's wurden nicht akzeptiert")
		Boolean acceptedAgb,

		@Min(value = 1, message = "Das Fachsemester muss zwischen 1 und 16 liegen")
		@Max(value = 16, message = "Das Fachsemester muss zwischen 1 und 16 liegen")
		@NotNull(message = "Bitte gebe dein derzeitiges Fachsemester ein")
		Integer semester
) {
	public AppUser toAppUser() {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
		String encodedPassword = encoder.encode(password);
		return new AppUser(null, UUID.randomUUID(), mail, username, encodedPassword, notificationSubscription != null && notificationSubscription, acceptedAgb, semester);
	}
}