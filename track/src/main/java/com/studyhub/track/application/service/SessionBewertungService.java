package com.studyhub.track.application.service;

import com.studyhub.track.domain.model.session.SessionBeendetEvent;
import com.studyhub.track.domain.model.session.SessionBewertung;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
public class SessionBewertungService {

	private final SessionBeendetEventRepository sessionBeendetEventRepository;

	public SessionBewertungService(SessionBeendetEventRepository sessionBeendetEventRepository) {
		this.sessionBeendetEventRepository = sessionBeendetEventRepository;
	}

	public Double getAverageKonzentrationBewertungByUsername(String username) {
		return getAverageByUsername(username, SessionBewertung::getKonzentrationBewertung);
	}

	public Double getAverageProduktivitaetBewertungByUsername(String username) {
		return getAverageByUsername(username, SessionBewertung::getProduktivitaetBewertung);
	}

	public Double getAverageSchwierigkeitBewertungByUsername(String username) {
		return getAverageByUsername(username, SessionBewertung::getSchwierigkeitBewertung);
	}

	public SessionBewertungStatistikDto getSessionBewertungStatistikByUsername(String username) {
		return new SessionBewertungStatistikDto(
			getAverageKonzentrationBewertungByUsername(username),
			getAverageProduktivitaetBewertungByUsername(username),
			getAverageSchwierigkeitBewertungByUsername(username)
		);
	}

	private Double getAverageByUsername(
			String username,
			Function<SessionBewertung, Integer> mapper) {

		List<SessionBeendetEvent> events = sessionBeendetEventRepository.findAllByUsername(username);

		if (events.isEmpty()) return 0.0;

		int sum = events.stream()
				.map(SessionBeendetEvent::getBewertung)
				.map(mapper)
				.reduce(0, Integer::sum);

		return sum / (double) events.size();
	}
}
