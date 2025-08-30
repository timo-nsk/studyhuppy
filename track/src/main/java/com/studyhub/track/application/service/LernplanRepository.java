package com.studyhub.track.application.service;

import com.studyhub.track.domain.model.lernplan.Lernplan;

import java.util.List;
import java.util.UUID;

public interface LernplanRepository {
	Lernplan save(Lernplan lernplan);
	Lernplan findActiveByUsername(String username);
	List<Lernplan> findAllByUsername(String username);
	void deleteByFachId(UUID fachId);
}
