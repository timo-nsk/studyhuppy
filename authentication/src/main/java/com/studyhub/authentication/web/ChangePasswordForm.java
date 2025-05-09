package com.studyhub.authentication.web;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record ChangePasswordForm(
		@NotBlank(message = "Kein Passwort eingegeben")
		@Length(message = "Passwort muss 8 Zeichen lang sein", min = 8)
		@CorrectPassword
		String oldPassword,

		@NotBlank(message = "Kein Passwort eingegeben")
		@Length(message = "Passwort muss 8 Zeichen lang sein", min = 8)
		String newPassword,

		@NotBlank(message = "Kein Passwort eingegeben")
		@Length(message = "Passwort muss 8 Zeichen lang sein", min = 8)
		String newPasswordRepeat) {
}
