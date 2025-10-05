package com.studyhub.kartei.adapter.db.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class KarteikarteGelerntEventDtoTest {

	@Test
	void shouldCreateKarteikarteGelerntEventDto() {
		Integer id = 1;
		UUID stapelId = UUID.randomUUID();
		UUID karteikarteId = UUID.randomUUID();
		LocalDateTime gelerntAm = LocalDateTime.now();
		int secondsNeeded = 120;

		KarteikarteGelerntEventDto dto = new KarteikarteGelerntEventDto(1, stapelId, karteikarteId, gelerntAm, secondsNeeded);

		assertNotNull(dto);
		assertEquals(id, dto.id());
		assertEquals(stapelId, dto.stapelId());
		assertEquals(karteikarteId, dto.karteikarteId());
		assertEquals(gelerntAm, dto.gelerntAm());
		assertEquals(secondsNeeded, dto.secondsNeeded());
	}

	@Test
	void shouldBeEqualIfSameValues() {
		Integer id = 1;
		UUID stapelId = UUID.randomUUID();
		UUID karteikarteId = UUID.randomUUID();
		LocalDateTime gelerntAm = LocalDateTime.now();
		int secondsNeeded = 120;

		KarteikarteGelerntEventDto dto1 = new KarteikarteGelerntEventDto(id, stapelId, karteikarteId, gelerntAm, secondsNeeded);
		KarteikarteGelerntEventDto dto2 = new KarteikarteGelerntEventDto(id, stapelId, karteikarteId, gelerntAm, secondsNeeded);

		assertEquals(dto1, dto2);
		assertEquals(dto1.hashCode(), dto2.hashCode());
	}

	@Test
	void shouldNotBeEqualIfDifferentValues() {
		KarteikarteGelerntEventDto dto1 = new KarteikarteGelerntEventDto(1, UUID.randomUUID(), UUID.randomUUID(), LocalDateTime.now(), 120);
		KarteikarteGelerntEventDto dto2 = new KarteikarteGelerntEventDto(2, UUID.randomUUID(), UUID.randomUUID(), LocalDateTime.now(), 150);

		assertNotEquals(dto1, dto2);
	}
}
