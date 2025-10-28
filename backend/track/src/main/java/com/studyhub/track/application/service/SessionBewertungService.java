package com.studyhub.track.application.service;

import com.studyhub.track.domain.model.session.SessionBeendetEvent;
import com.studyhub.track.domain.model.session.SessionBewertung;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

	public Map<Integer, Integer> getKonzentrationHisto(String username) {
		return getHistogramByUsername(username, SessionBewertung::getKonzentrationBewertung);
	}

	public Map<Integer, Integer> getProduktivitaetHist(String username) {
		return getHistogramByUsername(username, SessionBewertung::getProduktivitaetBewertung);
	}

	public Map<Integer, Integer> getSchwierigkeitHisto(String username) {
		return getHistogramByUsername(username, SessionBewertung::getSchwierigkeitBewertung);
	}

	public SessionBewertungGeneralStatistikDto getSessionBewertungStatistikByUsername(String username) {
		return new SessionBewertungGeneralStatistikDto(
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

	private Map<Integer, Integer> getHistogramByUsername(String username,
	                                                     Function<SessionBewertung, Integer> mapper) {
		List<SessionBeendetEvent> events = sessionBeendetEventRepository.findAllByUsername(username);
		Map<Integer, Integer> histogram = new HashMap<>();

		for (int i = 0; i <= 10; i++) histogram.put(i, 0);

		for (int i = 0; i <= 10; i++) {
			int j = i;
			events.stream()
					.map(SessionBeendetEvent::getBewertung)
					.map(mapper)
					.filter(bewertung -> bewertung == j)
					.forEach(bewertung -> histogram.put(j, histogram.get(j) + 1));
		}

		return histogram;
	}
}