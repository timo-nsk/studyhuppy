package com.studyhub.kartei.service.application;

import com.studyhub.kartei.adapter.db.KarteikarteGelerntEventDao;
import com.studyhub.kartei.adapter.db.KarteikarteGelerntEventRepoImpl;
import com.studyhub.kartei.domain.model.KarteikarteGelerntEvent;
import com.studyhub.kartei.service.application.lernzeit.KarteikarteGelerntEventRepository;
import org.junit.jupiter.api.BeforeAll;
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
import java.util.List;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestcontainersConfiguration.class)
@DataJdbcTest
@ActiveProfiles("test")
@Rollback(false)
@Sql(scripts = "drop_events.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "gelernt_events_init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class KarteikarteGelerntEventTest {

	@Autowired
	KarteikarteGelerntEventDao dao;

	KarteikarteGelerntEventRepository repo;

	@BeforeEach
	void init() {
		repo = new KarteikarteGelerntEventRepoImpl(dao);
	}

	@Test
	@DisplayName("Alle KarteikarteGelerntEvents einer Kartekarte können erfolgreich gelöscht werden")
	void test_1() {
		UUID karteToDelete = UUID.fromString("a12c9df0-6452-4f1e-9f53-cb1efdf934c1");
		List<KarteikarteGelerntEvent> l_before = repo.findByKarteikarteId(karteToDelete);

		repo.deleteAllByKarteiFachId(karteToDelete);

		List<KarteikarteGelerntEvent> l_after = repo.findByKarteikarteId(karteToDelete);

		assertThat(l_before).hasSize(4);
		assertThat(l_after).hasSize(0);
	}
}
