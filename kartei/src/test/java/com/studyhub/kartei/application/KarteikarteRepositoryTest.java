package com.studyhub.kartei.application;

import com.studyhub.kartei.adapter.db.KarteikarteDao;
import com.studyhub.kartei.adapter.db.KarteikarteRepositoryImpl;
import com.studyhub.kartei.domain.model.Karteikarte;
import com.studyhub.kartei.service.application.KarteikarteRepository;
import com.studyhub.kartei.util.KarteikarteMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.utility.TestcontainersConfiguration;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestcontainersConfiguration.class)
@DataJdbcTest
@ActiveProfiles("test")
@Rollback(false)
@Sql(scripts = "drop_kartei.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class KarteikarteRepositoryTest {

	@Autowired
	KarteikarteDao dao;

	KarteikarteRepository repository;

	@BeforeEach
	public void init() {
		repository = new KarteikarteRepositoryImpl(dao);
	}

	@Test
	@DisplayName("Eine Karteikarte kann erfolgreich abgespeichert werden")
	void test_1() {
		Karteikarte karte = KarteikarteMother.newKarteikarte("frage", "antwort");

		Karteikarte saved = repository.save(karte);

		assertThat(karte.getFachId()).isEqualTo(saved.getFachId());
	}

	@Test
	@DisplayName("Lernstufen einer vorhandenen Karteikarte kann erfolgreich aktualisiert werden")
	void test_2() {
		String neueLernstufen = "2d,4d,8d";
		Karteikarte karte = KarteikarteMother.newKarteikarte("frage", "antwort");
		repository.save(karte);

		repository.updateLernstufen(neueLernstufen, karte.getFachId().toString());

		Karteikarte found = repository.findByFachId(karte.getFachId().toString());
		assertThat(karte.getLernstufen()).isNotEqualTo(neueLernstufen);
		assertThat(found.getLernstufen()).isEqualTo(neueLernstufen);
	}

	@Test
	@DisplayName("Eine Single Choice Karteikarte kann erfolgreich gespeichert und gefunden werden")
	void test_3() {
		UUID karteId = UUID.randomUUID();
		UUID karteId2 = UUID.randomUUID();
		Karteikarte k = KarteikarteMother.newKarteWithAntworten(karteId, 3);
		Karteikarte k2 = KarteikarteMother.newKarteWithAntworten(karteId2, 2);
		Karteikarte saved = repository.save(k);
		repository.save(k2);

		Karteikarte found = repository.findByFachId(saved.getFachId().toString());

		assertThat(found).isEqualTo(saved);
		assertThat(found.getAntworten().size()).isEqualTo(3);
	}
}