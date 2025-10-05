package com.studyhub.kartei.adapter.db.mapper;

import com.studyhub.kartei.adapter.db.dto.KarteikarteGelerntEventDto;
import com.studyhub.kartei.domain.model.KarteikarteGelerntEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class KarteikarteGelerntEventMapperTest {

	@Test
	@DisplayName("toKarteikarteGelerntEventDto mapped korrekt")
	void test_1() {
		UUID stapelId = UUID.randomUUID();
		UUID karteikarteId = UUID.randomUUID();
		LocalDateTime gelerntAm = LocalDateTime.now();
		int secondsNeeded = 100;
		KarteikarteGelerntEvent event = new KarteikarteGelerntEvent(stapelId, karteikarteId, gelerntAm, secondsNeeded);

		KarteikarteGelerntEventDto dto = KarteikarteGelerntEventMapper.toKarteikarteGelerntEventDto(event);

		assertNotNull(dto);
		assertNull(dto.id());
		assertEquals(stapelId, dto.stapelId());
		assertEquals(karteikarteId, dto.karteikarteId());
		assertEquals(gelerntAm, dto.gelerntAm());
		assertEquals(secondsNeeded, dto.secondsNeeded());
	}

	@Test
	@DisplayName("toKarteikarteGelerntEvent mapped korrekt")
	void test_2() {
		UUID stapelId = UUID.randomUUID();
		UUID karteikarteId = UUID.randomUUID();
		LocalDateTime gelerntAm = LocalDateTime.now();
		int secondsNeeded = 120;

		KarteikarteGelerntEventDto dto = new KarteikarteGelerntEventDto(null, stapelId, karteikarteId, gelerntAm, secondsNeeded);

		KarteikarteGelerntEvent event = KarteikarteGelerntEventMapper.toKarteikarteGelerntEvent(dto);

		assertNotNull(event);
		assertEquals(stapelId, event.getStapelId());
		assertEquals(karteikarteId, event.getKarteikarteId());
		assertEquals(gelerntAm, event.getGelerntAm());
		assertEquals(secondsNeeded, event.getSecondsNeeded());
	}

	@Test
	@DisplayName("Wenn ein Objekt nicht gemapped werden kann, wird eine Exception geworfen")
	void test_3() {
		assertThrows(DtoMappingException.class, () ->
				KarteikarteGelerntEventMapper.toKarteikarteGelerntEventDto(null)
		);

		assertThrows(DtoMappingException.class, () ->
				KarteikarteGelerntEventMapper.toKarteikarteGelerntEvent(null)
		);
	}
}