package com.studyhub.track.adapter.db.session;

import com.studyhub.track.domain.model.session.SessionBeendetEvent;

public class SessionBeendetEventMapper {

	private SessionBeendetEventMapper() {
		// Utility class
	}

	public static SessionBeendetEvent toDomain(SessionBeendetEventDto dto) {
		return new SessionBeendetEvent(
				dto.eventId(),
				dto.sessionId(),
				dto.username(),
				dto.beendetDatum(),
				dto.bewertung(),
				dto.abgebrochen()
		);
	}

	public static SessionBeendetEventDto toDto(SessionBeendetEvent event) {
		return new SessionBeendetEventDto(
				null,
				event.getEventId(),
				event.getSessionId(),
				event.getUsername(),
				event.getBeendetDatum(),
				event.getBewertung(),
				event.getAbgebrochen()
		);
	}
}
