package com.studyhub.track.service;

import com.studyhub.track.adapter.db.modul.*;
import com.studyhub.track.application.service.ModulGelerntEventRepository;
import com.studyhub.track.domain.model.modul.ModulGelerntEvent;
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
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataJdbcTest
@Sql(scripts = "init_events_db_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@TestPropertySource(properties = {
		"spring.datasource.url=jdbc:postgresql://localhost:${container.port}/modultest",
		"spring.datasource.username=timo",
		"spring.datasource.password=1234"
})
public class ModulGelerntEventRepositoryTest {

	@Container
	static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15.2")
			.withDatabaseName("modultest")
			.withUsername("timo")
			.withPassword("1234");

	@DynamicPropertySource
	static void overrideProps(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", postgres::getJdbcUrl);
		registry.add("spring.datasource.username", postgres::getUsername);
		registry.add("spring.datasource.password", postgres::getPassword);
	}

	@Autowired
	ModulGelerntEventDao dao;

	ModulGelerntEventRepository repository;

	@BeforeEach
	void setUp() {
		repository = new ModulGelerntEventRepositoryImpl(dao);
	}

	@Test
	@DisplayName("Ein Event kann abgespeichert werden")
	void test_01() {
		ModulGelerntEvent event = new ModulGelerntEvent(UUID.randomUUID(), UUID.randomUUID(), "peter89", 20, LocalDate.now());

		ModulGelerntEvent saved = repository.save(event);

		assertThat(saved).isEqualTo(event);
	}

	@Test
	@DisplayName("Für einen User wird in einem Modul die Summe der gelernten Sekunden an einem bestimmten Datum berechnet und zurückgegeben")
	void test_02() {
		int secondsLearned = repository.getSumSecondsLearned(LocalDate.of(2025,3,1), "timo123", UUID.fromString("b3a1e8f2-7d6a-4b3e-90a8-b9e2345d6789"));
		assertThat(secondsLearned).isEqualTo(300);
	}

	@Test
	@DisplayName("Alle Events eines users werden gefunden")
	void test_03() {
		List<ModulGelerntEvent> l = repository.getAllByUsername("timo123");

		assertThat(l).hasSize(3);
	}

	@Test
	@DisplayName("Alle Events eines Moduls werden gelöscht")
	void test_4() {
		UUID modulId = UUID.fromString("b3a1e8f2-7d6a-4b3e-90a8-b9e2345d6789");
		String username = "timo123";

		repository.deleteAllByModulId(modulId);

		List<ModulGelerntEvent> events = repository.getAllByUsername(username);
		assertThat(events).hasSize(0);
	}
}