package com.studyhub.track.domain.model.lernplan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Lernplan {
	private UUID fachId;
	private String username;
	private String titel;
	private List<Tag> tagesListe;
	private boolean isActive;

	public void aktualisiereTagesliste(List<Tag> neueTagesListe) {
		this.tagesListe = neueTagesListe;
	}
}
