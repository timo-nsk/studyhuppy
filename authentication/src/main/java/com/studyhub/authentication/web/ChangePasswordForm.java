package com.studyhub.authentication.web;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record ChangePasswordForm(
		String userId,
		String oldPw,
		String newPw) {
}
