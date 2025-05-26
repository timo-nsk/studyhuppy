package com.studyhub.track.adapter.web.controller.api;

import com.studyhub.track.domain.model.modul.Modultermin;
import com.studyhub.track.domain.model.modul.Terminfrequenz;

import java.time.LocalDateTime;
import java.util.UUID;

public record NeuerModulterminRequest(
		UUID modulId,
		String titel,
		LocalDateTime beginn,
		LocalDateTime ende,
		String notiz,
		Terminfrequenz repeat
) {

	public Modultermin toModultermin() {
		return new Modultermin(
				titel,
				beginn,
				ende,
				notiz,
				repeat
		);
	}
}
