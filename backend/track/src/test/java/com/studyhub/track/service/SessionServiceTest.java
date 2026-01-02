package com.studyhub.track.service;

import com.studyhub.track.adapter.db.session.SessionDao;
import com.studyhub.track.adapter.db.session.SessionRepositoryImpl;
import com.studyhub.track.application.service.dto.SessionInfoDto;
import com.studyhub.track.domain.model.session.SessionRepository;
import com.studyhub.track.application.service.SessionService;
import com.studyhub.track.domain.model.session.Session;
import com.studyhub.track.util.SessionMother;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@Disabled("Probleme mit Config Server, muss beim testen laufen")
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

	@Test
	@DisplayName("Session wird gespeichert")
	void test_2() {
		Session s = SessionMother.createSessionWithNRandomBlocks(2);

		boolean saved = sessionService.save(s);

		assertThat(saved).isTrue();
	}

	@Test
	@DisplayName("Alle Sessions eines Users werden gefunden")
	void test_3() {
		String username = "alex";

		List<Session> userSessions = sessionService.getSessionsByUsername(username);

		assertThat(userSessions).hasSize(2);
	}

	@Test
	@DisplayName("Für eines User wird eine List<SessionInfoDto> für das Frontend korrekt erstellt")
	void test_4() {
		String username = "alex";
		List<SessionInfoDto> expected = new ArrayList<>();
		SessionInfoDto dto1 = new SessionInfoDto("22222222-2222-2222-2222-222222222222", "Programmieren-Session", 4200);
		SessionInfoDto dto2 = new SessionInfoDto("44444444-4444-4444-4444-444444444444", "Physik-Session", 2150);
		expected.add(dto1);
		expected.add(dto2);

		List<SessionInfoDto> actual = sessionService.getLernplanSessionDataOfUser(username);

		assertThat(actual).isEqualTo(expected);
	}

	@Test
	@DisplayName("Eine Session wird anhand Ihrer Id gefunden")
	void test_5() {
		UUID sessionId = UUID.fromString("33333333-3333-3333-3333-333333333333");

		Session foundSession = sessionService.getSessionByFachId(sessionId);

		assertThat(foundSession).isNotNull();
		assertThat(foundSession.getTitel()).isEqualTo("Bio-Session");
	}
}
