package com.studyhub.kartei.service.application;

import com.studyhub.kartei.domain.model.KarteikarteGelerntEvent;
import com.studyhub.kartei.domain.model.Schwierigkeit;

import java.time.LocalDateTime;
import java.util.UUID;

public record UpdateInfo(String stapelId,
                         String karteId,
                         Schwierigkeit schwierigkeit,
                         Integer secondsNeeded) {

	public KarteikarteGelerntEvent prepareHappenedEvent() {
		return new KarteikarteGelerntEvent(UUID.fromString(stapelId()), UUID.fromString(karteId()), LocalDateTime.now(), secondsNeeded());
	}
}
