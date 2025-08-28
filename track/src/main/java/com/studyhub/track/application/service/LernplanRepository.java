package com.studyhub.track.application.service;

import com.studyhub.track.domain.model.lernplan.Lernplan;

public interface LernplanRepository {
	Lernplan save(Lernplan lernplan);

	Lernplan findActiveByUsername(String username);
}
