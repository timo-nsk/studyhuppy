package com.studyhub.track.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class LernplanAktivierungsService {

	private final LernplanRepository lernplanRepository;

	public LernplanAktivierungsService(LernplanRepository lernplanRepository) {
		this.lernplanRepository = lernplanRepository;
	}

	@Transactional
	public void setActiveLernplan(UUID fachId, String username) {
		lernplanRepository.deactivateAllByUsername(username);
		lernplanRepository.setIsActiveOfLernplan(fachId, true);
	}
}
