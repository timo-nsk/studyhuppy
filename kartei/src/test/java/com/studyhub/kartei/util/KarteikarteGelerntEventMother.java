package com.studyhub.kartei.util;

import com.studyhub.kartei.domain.model.Karteikarte;
import com.studyhub.kartei.domain.model.KarteikarteGelerntEvent;
import com.studyhub.kartei.domain.model.Stapel;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class KarteikarteGelerntEventMother {



	public static List<KarteikarteGelerntEvent> initEvents(UUID stapelId) {
		List<KarteikarteGelerntEvent> events = new LinkedList<>();

		KarteikarteGelerntEvent event1 = eventWith(stapelId, UUID.fromString("3f29a9a6-5b76-4cbf-bde0-ef6a64d3d90f"), 51);
		KarteikarteGelerntEvent event2 = eventWith(stapelId, UUID.fromString("3f29a9a6-5b76-4cbf-bde0-ef6a64d3d90f"), 41);
		KarteikarteGelerntEvent event3 = eventWith(stapelId, UUID.fromString("3f29a9a6-5b76-4cbf-bde0-ef6a64d3d90f"), 31); //41

		KarteikarteGelerntEvent event4 = eventWith(stapelId, UUID.fromString("5e8e5c2b-ec90-4b70-9a0c-ff9e9478b4c8"), 51);
		KarteikarteGelerntEvent event5 = eventWith(stapelId, UUID.fromString("5e8e5c2b-ec90-4b70-9a0c-ff9e9478b4c8"), 52);
		KarteikarteGelerntEvent event6 = eventWith(stapelId, UUID.fromString("5e8e5c2b-ec90-4b70-9a0c-ff9e9478b4c8"), 53); //52

		events.add(event1);
		events.add(event2);
		events.add(event3);
		events.add(event4);
		events.add(event5);
		events.add(event6);

		return events;
	}

	public static List<KarteikarteGelerntEvent> initEventsWithSeconds(UUID stapelId, int seconds) {
		List<KarteikarteGelerntEvent> events = new LinkedList<>();

		KarteikarteGelerntEvent event1 = eventWith(stapelId, UUID.fromString("0e7c40f7-6891-41f6-94cb-dc1ec946c774"), seconds);
		KarteikarteGelerntEvent event2 = eventWith(stapelId, UUID.fromString("0e7c40f7-6891-41f6-94cb-dc1ec946c774"), seconds);
		KarteikarteGelerntEvent event3 = eventWith(stapelId, UUID.fromString("0e7c40f7-6891-41f6-94cb-dc1ec946c774"), seconds);

		KarteikarteGelerntEvent event4 = eventWith(stapelId, UUID.fromString("5e8e5c2b-ec90-4b70-9a0c-ff9e9478b4c8"), seconds);
		KarteikarteGelerntEvent event5 = eventWith(stapelId, UUID.fromString("5e8e5c2b-ec90-4b70-9a0c-ff9e9478b4c8"), seconds);
		KarteikarteGelerntEvent event6 = eventWith(stapelId, UUID.fromString("5e8e5c2b-ec90-4b70-9a0c-ff9e9478b4c8"), seconds);

		events.add(event1);
		events.add(event2);
		events.add(event3);
		events.add(event4);
		events.add(event5);
		events.add(event6);

		return events;
	}

	public static Stapel initStapelWithKartenAndId(UUID stapelId) {
		List<Karteikarte> karten = new LinkedList<>();

		LocalDateTime fällig1 = LocalDateTime.of(2025, 1, 10, 10, 0);
		LocalDateTime fällig2 = LocalDateTime.of(2025, 1, 20, 10, 0);

		Karteikarte k1 = KarteikarteMother.newKarteWithIdAndFälligAm(UUID.fromString("3f29a9a6-5b76-4cbf-bde0-ef6a64d3d90f"), fällig1);
		Karteikarte k2 = KarteikarteMother.newKarteWithIdAndFälligAm(UUID.fromString("5e8e5c2b-ec90-4b70-9a0c-ff9e9478b4c8"), fällig2);

		karten.add(k1);
		karten.add(k2);

		Stapel stapel = new Stapel(stapelId, UUID.randomUUID(), "name", "beschreibung", "2m,2h,2d", "user1", karten);

		return stapel;
	}

	public static KarteikarteGelerntEvent eventWith(UUID stapelId, UUID karteId, int secondsNeeded) {
		return new KarteikarteGelerntEvent(stapelId,karteId, LocalDateTime.now(), secondsNeeded);
	}
}
