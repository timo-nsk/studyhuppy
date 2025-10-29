package com.studyhub.track.domain.service;

import com.studyhub.track.application.service.LernplanRepository;
import com.studyhub.track.application.service.SessionRepository;
import com.studyhub.track.domain.model.lernplan.Lernplan;
import com.studyhub.track.domain.model.lernplan.Tag;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class SessionLoeschenService {

	private SessionRepository sessionRepository;
	private LernplanRepository lernplanRepository;

	public SessionLoeschenService(SessionRepository sessionRepository, LernplanRepository lernplanRepository) {
		this.sessionRepository = sessionRepository;
		this.lernplanRepository = lernplanRepository;
	}

	@Transactional
	public void sessionLoeschen(UUID sessionId, String username) {
		sessionRepository.deleteByFachId(sessionId);
		List<Lernplan> lernplaene = lernplanRepository.findAllByUsername(username);

		for (Lernplan lernplan : lernplaene) {
			List<Tag> tage = lernplan.getTagesListe();

			boolean removed = tage.removeIf(tag -> sessionId.equals(tag.getSessionId()));

			if(removed) {
				lernplanRepository.save(lernplan);
				removed = false;
			}
		}
	}
}
