package com.studyhub.track.adapter.db.modul;

import com.studyhub.track.domain.model.modul.ModulGelerntEvent;

public class ModulGelerntEventMapper {

	private ModulGelerntEventMapper() {
		// Utility class
	}

	public static ModulGelerntEventDto toDto(ModulGelerntEvent modulGelerntEvent) {
		return new ModulGelerntEventDto(
				null,
				modulGelerntEvent.eventId(),
				modulGelerntEvent.modulId(),
				modulGelerntEvent.username(),
				modulGelerntEvent.secondsLearned(),
				modulGelerntEvent.dateGelernt()
		);
	}

	public static ModulGelerntEvent toModulGelerntEvent(ModulGelerntEventDto dto) {
		return new ModulGelerntEvent(
				dto.eventId(),
				dto.modulId(),
				dto.username(),
				dto.secondsLearned(),
				dto.dateGelernt()
		);
	}
}
