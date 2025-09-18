package com.studyhub.track.adapter.db.session;

import com.studyhub.track.domain.model.session.Session;

public class SessionMapper {

	private SessionMapper() {
		// Utility class
	}

	public static SessionDto toDto(Session session, Long existingDbKey) {
		if (session == null) {
			return null;
		}
		return new SessionDto(
				existingDbKey,
				session.getFachId(),
				session.getUsername(),
				session.getTitel(),
				session.getBeschreibung(),
				session.getBlocks()
		);
	}

	public static Session toEntity(SessionDto sessionDto) {
		if (sessionDto == null) {
			return null;
		}
		return new Session(
				sessionDto.fachId(),
				sessionDto.username(),
				sessionDto.titel(),
				sessionDto.beschreibung(),
				sessionDto.blocks()
		);
	}
}
