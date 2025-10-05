package com.studyhub.kafka.dto;

import java.util.UUID;

public record UserDto(
		UUID userId,
		String username
) {
}
