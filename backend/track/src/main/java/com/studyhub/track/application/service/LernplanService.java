package com.studyhub.track.application.service;

import com.studyhub.track.application.service.dto.LernplanWochenuebersicht;
import com.studyhub.track.application.service.dto.LernplanTagesuebersicht;
import com.studyhub.track.domain.model.lernplan.Lernplan;
import com.studyhub.track.domain.model.lernplan.LernplanRepository;
import com.studyhub.track.domain.model.lernplan.Tag;
import com.studyhub.track.domain.model.session.Session;
import com.studyhub.track.domain.model.session.SessionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class LernplanService {

	private final LernplanRepository lernplanRepository;
	private final SessionRepository sessionRepository;

	public LernplanService(LernplanRepository lernplanRepository, SessionRepository sessionRepository) {
		this.lernplanRepository = lernplanRepository;
		this.sessionRepository = sessionRepository;
	}

	public boolean saveLernplan(Lernplan lernplan) {
		Lernplan saved = lernplanRepository.save(lernplan);
		return saved != null;
	}

	public LernplanWochenuebersicht collectLernplanWochenuebersicht(String username) {
		Lernplan entityLerntag = lernplanRepository.findActiveByUsername(username);

		if (entityLerntag == null) return null;

		List<LernplanTagesuebersicht> sessions = new ArrayList<>();
		for(Tag tag : entityLerntag.getTagesListe()) {
			UUID sessionId = tag.getSessionId();
			Session session = sessionRepository.findSessionByFachId(sessionId);
			String dayString = "";
			switch (tag.getTag()) {
				case MONDAY -> dayString = "Montags";
				case TUESDAY -> dayString = "Dienstags";
				case WEDNESDAY -> dayString = "Mittwochs";
				case THURSDAY -> dayString = "Donnerstags";
				case FRIDAY -> dayString = "Freitags";
				case SATURDAY -> dayString = "Samstags";
				case SUNDAY -> dayString = "Sonntags";
			}

			sessions.add(new LernplanTagesuebersicht(
					dayString,
					tag.getBeginn().toString(),
					session.getFachId().toString(),
					session.getBlocks()
			));
		}

		return new LernplanWochenuebersicht(entityLerntag.getTitel(), sessions);

	}

	public List<Lernplan> findAllLernplaeneByUsername(String username) {
		return lernplanRepository.findAllByUsername(username);
	}

	public int deleteLernplanByFachId(UUID fachId) {
		return lernplanRepository.deleteByFachId(fachId);
	}

	public Lernplan findByFachId(UUID lernplanId) {
		return lernplanRepository.findByFachId(lernplanId);
	}

	public boolean saveBearbeitetenLernplan(Lernplan bearbeiteterPlan) {
		UUID lernplanId = bearbeiteterPlan.getFachId();
		Lernplan alterPlan = lernplanRepository.findByFachId(lernplanId);
		alterPlan.aktualisiereTagesliste(bearbeiteterPlan.getTagesListe());
		Lernplan saved = lernplanRepository.save(alterPlan);
		return saved != null;
	}
}
