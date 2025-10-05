package com.studyhub.track.adapter.web.controller.request.dto;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public record TimerRequest(
		String modulId,
		String startDateMillis
) {
	public int toSeconds() {
		long timestampMillis = Long.parseLong(startDateMillis);
		Instant instant = Instant.ofEpochMilli(timestampMillis);
		LocalDateTime clientTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
		LocalDateTime serverTime = LocalDateTime.now();
		Duration duration = Duration.between(clientTime, serverTime);

		return (int) (duration.toMillis() / 1000.0);
	}
}
