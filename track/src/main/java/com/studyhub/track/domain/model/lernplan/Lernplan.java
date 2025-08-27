package com.studyhub.track.domain.model.lernplan;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Lernplan {
	private UUID fachId;
	private String username;
	private String titel;
	private List<Tag> tagesListe;
	private boolean isActive;
}
