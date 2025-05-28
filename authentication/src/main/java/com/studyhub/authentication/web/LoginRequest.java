package com.studyhub.authentication.web;

import jakarta.validation.constraints.NotBlank;


public record LoginRequest(
		String username,
		String password
) {
}
