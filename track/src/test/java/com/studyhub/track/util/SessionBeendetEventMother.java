package com.studyhub.track.util;

import com.studyhub.track.domain.model.session.SessionBeendetEvent;
import com.studyhub.track.domain.model.session.SessionBewertung;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SessionBeendetEventMother {

	public static List<SessionBeendetEvent> initEventsOfUser(String username) {
		LocalDateTime someDate = LocalDateTime.now();
		List<SessionBewertung> bewertungen = initBewertungen();

		List<SessionBeendetEvent> events = new ArrayList<>();

		for (int i = 0; i < bewertungen.size(); i++) {
			events.add(new SessionBeendetEvent(
				UUID.randomUUID(),
				username,
				someDate,
				bewertungen.get(i),
				false
			));
		}

		return events;
	}

	public static List<SessionBeendetEvent> initEventsOfUserHisto(String username) {
		LocalDateTime someDate = LocalDateTime.now();
		List<SessionBewertung> bewertungen = initBewertungenHisto();

		List<SessionBeendetEvent> events = new ArrayList<>();

		for (int i = 0; i < bewertungen.size(); i++) {
			events.add(new SessionBeendetEvent(
					UUID.randomUUID(),
					username,
					someDate,
					bewertungen.get(i),
					false
			));
		}

		return events;
	}

	public static List<SessionBewertung> initBewertungen() {
		SessionBewertung b1 = new SessionBewertung(5, 6, 7);
		SessionBewertung b2 = new SessionBewertung(5, 6, 7);
		SessionBewertung b3 = new SessionBewertung(5, 6, 7);
		SessionBewertung b4 = new SessionBewertung(5, 6, 7);
		SessionBewertung b5 = new SessionBewertung(5, 6, 7);
		SessionBewertung b6 = new SessionBewertung(5, 6, 7);
		SessionBewertung b7 = new SessionBewertung(5, 6, 7);
		SessionBewertung b8 = new SessionBewertung(5, 6, 7);
		return List.of(b1, b2, b3, b4, b5, b6, b7, b8);
	}

	public static List<SessionBewertung> initBewertungenHisto() {
		SessionBewertung b1 = new SessionBewertung(2, 1, 7);
		SessionBewertung b2 = new SessionBewertung(5, 1, 7);
		SessionBewertung b3 = new SessionBewertung(2, 1, 7);
		SessionBewertung b4 = new SessionBewertung(5, 3, 8);
		SessionBewertung b5 = new SessionBewertung(6, 3, 8);
		SessionBewertung b6 = new SessionBewertung(6, 3, 7);
		SessionBewertung b7 = new SessionBewertung(7, 0, 7);
		SessionBewertung b8 = new SessionBewertung(9, 10, 7);
		return List.of(b1, b2, b3, b4, b5, b6, b7, b8);
	}
}
