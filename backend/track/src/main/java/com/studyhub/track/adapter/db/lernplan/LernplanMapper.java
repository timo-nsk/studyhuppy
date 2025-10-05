package com.studyhub.track.adapter.db.lernplan;

import com.studyhub.track.domain.model.lernplan.Lernplan;

public class LernplanMapper {

	private LernplanMapper() {
		// Utility class
	}

	public static Lernplan toDomain(LernplanDto dto) {
		return new Lernplan(
				dto.fachId(),
				dto.username(),
				dto.titel(),
				dto.tagesListe(),
				dto.isActive()
		);
	}

	public static LernplanDto toDto(Lernplan l, Long existingId) {
		return new LernplanDto(
				existingId,
				l.getFachId(),
				l.getUsername(),
				l.getTitel(),
				l.getTagesListe(),
				l.isActive()
		);
	}
}
