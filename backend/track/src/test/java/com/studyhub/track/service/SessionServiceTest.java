package com.studyhub.track.service;

import com.studyhub.track.adapter.db.session.SessionDao;
import com.studyhub.track.adapter.db.session.SessionRepositoryImpl;
import com.studyhub.track.domain.model.session.SessionRepository;
import com.studyhub.track.application.service.SessionService;
import com.studyhub.track.domain.model.session.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;


@Disabled("probleme mit H2")
@DataJdbcTest
@Rollback(false)
@Sql(scripts = "drop_session_table.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "init_session_db_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles("test")
class SessionServiceTest {
	@Autowired
	SessionDao sessionRepository;

	SessionRepository repository;

	SessionService sessionService;

	@BeforeEach
	void setUp() {
		repository = new SessionRepositoryImpl(sessionRepository);
		sessionService = new SessionService(repository);
	}

	@Test
	@DisplayName("Alle Blöcke einer Session mit einer bestimmten Modul-Id werden erfolgreich aus der Session gelöscht und wieder in die Datenbank gespeichert")
	void test_1() {
		UUID modulId = UUID.fromString("bbbbbbbb-cccc-dddd-eeee-ffffffffffff");
		UUID sessionId = UUID.fromString("55555555-5555-5555-5555-555555555555");
		int beforeAlteringSize = repository.findSessionByFachId(sessionId).getBlocks().size();
		String username = "timo";

		sessionService.deleteModuleFromBlocks(modulId, username);

		Session alteredSession = repository.findSessionByFachId(sessionId);

		assertThat(beforeAlteringSize)
				.as("Session hat 2 Blöcke")
				.isEqualTo(2);

		assertThat(alteredSession.getBlocks())
				.as("Nach der Löschung hat die Session nur noch einen Block")
				.hasSize(1);
	}
}
