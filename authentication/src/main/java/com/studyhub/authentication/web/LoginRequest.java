package com.studyhub.authentication.web;

public record LoginRequest(
		String username,
		String password
) {
}
