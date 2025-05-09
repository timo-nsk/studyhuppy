package com.studyhub.kartei.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class KarteikarteGelerntEvent {
	private UUID stapelId;
	private UUID karteikarteId;
	private LocalDateTime gelerntAm;
	private int secondsNeeded;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof KarteikarteGelerntEvent that)) return false;
		return getSecondsNeeded() == that.getSecondsNeeded() && Objects.equals(getStapelId(), that.getStapelId()) && Objects.equals(getKarteikarteId(), that.getKarteikarteId()) && Objects.equals(getGelerntAm(), that.getGelerntAm());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getStapelId(), getKarteikarteId(), getGelerntAm(), getSecondsNeeded());
	}
}
