package com.studyhub.track.application.service;

import com.studyhub.track.domain.model.lernplan.Lernplan;
import org.springframework.stereotype.Service;

@Service
public class LernplanService {

	private final LernplanRepository lernplanRepository;

	public LernplanService(LernplanRepository lernplanRepository) {
		this.lernplanRepository = lernplanRepository;
	}

	public boolean saveLernplan(Lernplan lernplan) {
		Lernplan saved = lernplanRepository.save(lernplan);
		return saved != null;
	}
}
