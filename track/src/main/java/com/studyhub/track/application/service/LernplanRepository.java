package com.studyhub.track.application.service;

import com.studyhub.track.domain.model.lernplan.Lernplan;

import java.util.List;

public interface LernplanRepository {
	Lernplan save(Lernplan lernplan);
	Lernplan findActiveByUsername(String username);
	List<Lernplan> findAllByUsername(String username);
}
