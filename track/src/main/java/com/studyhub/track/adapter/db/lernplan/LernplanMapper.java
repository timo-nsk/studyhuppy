package com.studyhub.track.adapter.db.lernplan;

import com.studyhub.track.domain.model.lernplan.Lernplan;

public class LernplanMapper {

	public static Lernplan toDomain(LernplanDto dto) {
		return new Lernplan(
				dto.fachId(),
				dto.username(),
				dto.titel(),
				dto.tagesListe(),
				dto.isActive()
		);
	}

	public static LernplanDto toDto(Lernplan l) {
		return new LernplanDto(
				null,
				l.getFachId(),
				l.getUsername(),
				l.getTitel(),
				l.getTagesListe(),
				l.isActive()
		);
	}
}
