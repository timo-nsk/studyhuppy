package com.studyhub.kartei.adapter.web.controller.request.dto;

import com.studyhub.kartei.domain.model.Stapel;

import java.util.UUID;

public record CreateNewStapelRequest(String modulFachId,
                                     String setName,
                                     String beschreibung,
                                     String lernIntervalle) {

	public Stapel toNewStapel(String username) {
		if (modulFachId == null) {
			return new Stapel(null, setName(), beschreibung(), username, lernIntervalle());
		} else {
			return new Stapel(UUID.fromString(modulFachId()), setName(), beschreibung(), username, lernIntervalle());
		}
	}
}
