package com.studyhub.kartei.service.application.lernzeit;

import com.studyhub.kartei.adapter.db.KarteikarteGelerntEventDao;
import com.studyhub.kartei.adapter.db.KarteikarteGelerntEventRepoImpl;
import com.studyhub.kartei.domain.model.KarteikarteGelerntEvent;
import com.studyhub.kartei.service.application.lernzeit.KarteikarteGelerntEventRepository;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.utility.TestcontainersConfiguration;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestcontainersConfiguration.class)
@DataJdbcTest
public class KarteikarteGelerntEventRepositoryTest {

	@Autowired
	KarteikarteGelerntEventDao dao;

	KarteikarteGelerntEventRepository repo;

	@BeforeEach
	void init() {
		repo = new KarteikarteGelerntEventRepoImpl(dao);
	}

	@Test
	@DisplayName("Ein Event kann erfolgreich abgespeichert werden")
	void test_1() {
		KarteikarteGelerntEvent event = new KarteikarteGelerntEvent(UUID.randomUUID(), UUID.randomUUID(), LocalDateTime.now(), 100);

		KarteikarteGelerntEvent saved = repo.save(event);

		assertThat(saved).isEqualTo(event);
	}

	@Test
	@DisplayName("Alle KarteikarteGelerntEvents für eine bestimmte Karteikarte werden gefunden")
	@Sql("init_event_db.sql")
	void test_2() {
		UUID karteikarteId = UUID.fromString("a1b909b7-6af2-4724-8dd9-7f5b2b338524");

		List<KarteikarteGelerntEvent> saved = repo.findByKarteikarteId(karteikarteId);

		assertThat(saved.size()).isEqualTo(7);
	}

	@Test
	@DisplayName("Liefere die Summe der secondsNeeded einer Karteikarte wieder")
	@Sql("init_event_db.sql")
	void test_3() {
		UUID karteikarteId = UUID.fromString("a1b909b7-6af2-4724-8dd9-7f5b2b338524");

		int summe = repo.sumOfSecondsNeededForKarteikarte(karteikarteId);

		assertThat(summe).isEqualTo(489);
	}

	@Test
	@DisplayName("Liefere die Anzahl der Events für eine Karteikarte wieder")
	@Sql("init_event_db.sql")
	void test_4() {
		UUID karteikarteId = UUID.fromString("a1b909b7-6af2-4724-8dd9-7f5b2b338524");

		int anzahl = repo.coundByKarteikarteId(karteikarteId);

		assertThat(anzahl).isEqualTo(7);
	}

	@Test
	@DisplayName("Liefere die durchschnittliche Zeit (Sekunden), die für eine Karteikarte benötigt wurde")
	@Sql("init_event_db.sql")
	void test_5() {
		UUID karteikarteId = UUID.fromString("a1b909b7-6af2-4724-8dd9-7f5b2b338524");

		double average = repo.getAverageSecondsNeededForKarte(karteikarteId);

		assertThat(average).isCloseTo(69.857, Percentage.withPercentage(1));
	}

	@Test
	@DisplayName("Finde alle Events anhand des Stapels")
	@Sql("init_event_db.sql")
	void test_6() {
		UUID stapelId = UUID.fromString("a6084e8a-162d-4bc9-a458-af5e9a95d923");

		List<KarteikarteGelerntEvent> events = repo.findByStapelId(stapelId);

		assertThat(events.size()).isEqualTo(7);
	}
}
