package com.studyhub.track.adapter.web.controller.api;

import com.studyhub.track.domain.model.modul.Modultermin;
import com.studyhub.track.domain.model.modul.Terminfrequenz;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class NeuerModulterminRequest {
	private UUID modulId;
	private String titel;
	private LocalDateTime beginn;
	private LocalDateTime ende;
	private String notiz;
	private Terminfrequenz repeat;

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
