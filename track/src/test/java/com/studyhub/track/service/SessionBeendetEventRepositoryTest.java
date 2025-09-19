package com.studyhub.track.service;

import com.studyhub.track.adapter.db.session.SessionBeendetEventDao;
import com.studyhub.track.adapter.db.session.SessionBeendetEventRepositoryImpl;
import com.studyhub.track.application.service.SessionBeendetEventRepository;
import com.studyhub.track.domain.model.session.SessionBeendetEvent;
import com.studyhub.track.domain.model.session.SessionBewertung;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataJdbcTest
@Sql(scripts = "init_session_beendet_events_db_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@TestPropertySource(properties = {
		"spring.datasource.url=jdbc:postgresql://localhost:${container.port}/sessiontest",
		"spring.datasource.username=timo",
		"spring.datasource.password=1234"
})
class SessionBeendetEventRepositoryTest {

	@Container
	static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15.2")
			.withDatabaseName("sessiontest")
			.withUsername("timo")
			.withPassword("1234");

	@DynamicPropertySource
	static void overrideProps(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", postgres::getJdbcUrl);
		registry.add("spring.datasource.username", postgres::getUsername);
		registry.add("spring.datasource.password", postgres::getPassword);
	}

	@Autowired
	SessionBeendetEventDao dao;

	SessionBeendetEventRepository repository;

	@BeforeEach
	void setUp() {
		repository = new SessionBeendetEventRepositoryImpl(dao);
	}

	@Test
	@DisplayName("Ein neues SessionBeendetEvent eines Users wird erfolgreich gespeichert")
	void test_01() {
		SessionBewertung newBewertung = new SessionBewertung(5, 6, 5);
		SessionBeendetEvent newEvent = new SessionBeendetEvent(UUID.randomUUID(), "florentiiina", LocalDateTime.now(), newBewertung, false);

		SessionBeendetEvent savedEvent = repository.save(newEvent);

		assertThat(savedEvent).isNotNull();
	}

	@Test
	@DisplayName("Alle SessionBeendetEvents eines Users werden erfolgreich geladen")
	void test_02() {
		List<SessionBeendetEvent> allEvents = repository.findAllByUsername("john_doe");

		assertThat(allEvents).hasSize(3);
	}

	@Test
	@DisplayName("Ein SessionBeendetEvent wird anhand seiner Event-Id erfolgreich geladen")
	void test_03() {
		SessionBeendetEvent loadedEvent = repository.findByEventId(UUID.fromString("11111111-1111-1111-1111-111111111112"));

		assertThat(loadedEvent.getUsername()).isEqualTo("maria88");
	}

	@Test
	@DisplayName("Wenn in der Datenbank nach einer Event-Id gesucht wird, die nicht existiert, wird ein null-Wert zurückgegeben")
	void test_04() {
		SessionBeendetEvent loadedEvent = repository.findByEventId(UUID.fromString("11111111-1111-1111-1111-911111111112"));

		assertThat(loadedEvent).isNull();
	}

	@Test
	@DisplayName("Alle SessionBeendetEvents eines Users werden gelöscht")
	void test_05() {
		repository.deleteAllByUsername("peter66");

		List<SessionBeendetEvent> allEvents = repository.findAllByUsername("peter66");

		assertThat(allEvents).isEmpty();
	}
}
