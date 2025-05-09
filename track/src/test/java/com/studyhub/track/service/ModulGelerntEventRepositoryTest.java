package com.studyhub.track.service;

import com.studyhub.track.adapter.db.modul.*;
import com.studyhub.track.application.service.ModulGelerntEventRepository;
import com.studyhub.track.domain.model.modul.ModulGelerntEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.utility.TestcontainersConfiguration;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestcontainersConfiguration.class)
@DataJdbcTest
public class ModulGelerntEventRepositoryTest {

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

	@Sql("events.sql")
	@Test
	@DisplayName("Für einen User wird in einem Modul die Summe der gelernten Sekunden an einem bestimmten Datum berechnet und zurückgegeben")
	void test_02() {
		int secondsLearned = repository.getSumSecondsLearned(LocalDate.of(2025,3,1), "timo123", UUID.fromString("b3a1e8f2-7d6a-4b3e-90a8-b9e2345d6789"));
		assertThat(secondsLearned).isEqualTo(300);
	}
}
