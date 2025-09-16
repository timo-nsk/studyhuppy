package com.studyhub.track.adapter.web.controller.request.dto;

import com.studyhub.track.domain.model.session.SessionBeendetEvent;
import com.studyhub.track.domain.model.session.SessionBewertung;

import java.time.LocalDateTime;
import java.util.UUID;

public record SessionBeendetEventRequest(
		String username,
		SessionBewertung bewertung,
		Boolean abgebrochen
) {
	public SessionBeendetEvent toEntity() {
		UUID eventId = UUID.randomUUID();
		LocalDateTime beendetDatum = LocalDateTime.now();
		return new SessionBeendetEvent(eventId, username, beendetDatum, bewertung, abgebrochen);
	}
}
