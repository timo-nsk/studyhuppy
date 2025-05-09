package com.studyhub.kartei.adapter.web.controller;

import com.studyhub.kartei.domain.model.Stapel;

import java.util.UUID;

public record CreateNewStapelRequest(String modulFachId,
                                     String setName,
                                     String beschreibung,
                                     String lernIntervalle,
                                     String username) {

	public Stapel toNewStapel() {
		return new Stapel(UUID.fromString(modulFachId()), setName(), beschreibung(), username(), lernIntervalle());
	}
}
