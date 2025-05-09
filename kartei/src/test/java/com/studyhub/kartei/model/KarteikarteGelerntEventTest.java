package com.studyhub.kartei.model;

import com.studyhub.kartei.domain.model.KarteikarteGelerntEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class KarteikarteGelerntEventTest {

	@Test
	@DisplayName("Korrekte Instanziierung")
	void test_1() {
		UUID stapelId = UUID.randomUUID();
		UUID karteikarteId = UUID.randomUUID();
		LocalDateTime gelerntAm = LocalDateTime.now();
		int secondsNeeded = 120;

		KarteikarteGelerntEvent event = new KarteikarteGelerntEvent(stapelId, karteikarteId, gelerntAm, secondsNeeded);

		assertThat(event.getStapelId()).isEqualTo(stapelId);
		assertThat(event.getKarteikarteId()).isEqualTo(karteikarteId);
		assertThat(event.getGelerntAm()).isEqualTo(gelerntAm);
		assertThat(event.getSecondsNeeded()).isEqualTo(secondsNeeded);
	}

	@Test
	@DisplayName("Equals/Hash funktioniert")
	void test_2() {
		UUID stapelId = UUID.randomUUID();
		UUID karteikarteId = UUID.randomUUID();
		LocalDateTime gelerntAm = LocalDateTime.now();
		int secondsNeeded = 100;

		KarteikarteGelerntEvent event1 = new KarteikarteGelerntEvent(stapelId, karteikarteId, gelerntAm, secondsNeeded);
		KarteikarteGelerntEvent event2 = new KarteikarteGelerntEvent(stapelId, karteikarteId, gelerntAm, secondsNeeded);
		KarteikarteGelerntEvent eventDifferent = new KarteikarteGelerntEvent(UUID.randomUUID(), karteikarteId, gelerntAm, secondsNeeded);

		assertThat(event1).isEqualTo(event2);
		assertThat(event1.hashCode()).isEqualTo(event2.hashCode());
		assertThat(event1).isNotEqualTo(eventDifferent);
		assertThat(event1.hashCode()).isNotEqualTo(eventDifferent.hashCode());
	}
}
