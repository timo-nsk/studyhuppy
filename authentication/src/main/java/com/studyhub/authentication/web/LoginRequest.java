package com.studyhub.authentication.web;

import jakarta.validation.constraints.NotBlank;


public record LoginRequest(
		@NotBlank(message = "Kein Username eingegeben")
		String username,
		@NotBlank(message = "Kein Passwort eingegeben")
		String password
) {
}
