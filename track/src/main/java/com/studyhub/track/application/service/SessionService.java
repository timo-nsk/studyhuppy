package com.studyhub.track.application.service;

import com.studyhub.track.adapter.web.controller.request.dto.SessionInfoDto;
import com.studyhub.track.domain.model.session.Session;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SessionService {

	private final SessionRepository sessionRepository;

	public SessionService(SessionRepository sessionRepository) {
		this.sessionRepository = sessionRepository;
	}

	public boolean save(Session session) {
		Session saved = sessionRepository.save(session);
		return saved != null;
	}

	public List<Session> getSessionsByUsername(String username) {
		return sessionRepository.findAllByUsername(username);
	}

	public void deleteSession(UUID fachId) {
		sessionRepository.deleteByFachId(fachId);
	}

	public List<SessionInfoDto> getLernplanSessionDataOfUser(String username) {
		List<Session> sessions = sessionRepository.findAllByUsername(username);
		return sessions.stream()
				.map(session -> new SessionInfoDto(
						session.getFachId().toString(),
						session.getTitel(),
						session.getTotalZeit()
				))
				.toList();
	}

	public Session getSessionByFachId(UUID sessionId) {
		return sessionRepository.findSessionByFachId(sessionId);
	}
}
