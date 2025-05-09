package com.studyhub.kartei.adapter.db.dto;

import com.studyhub.kartei.domain.model.Karteikarte;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static com.studyhub.kartei.util.KarteikarteMother.newKarteikarte;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StapelDtoTest {

	@Test
	void testKarteikartenSetDtoCreation() {
		UUID fachId = UUID.randomUUID();
		UUID modulFachId = UUID.randomUUID();
		List<Karteikarte> karteikarten = List.of(newKarteikarte("f", "a"));

		StapelDto dto = new StapelDto(1, fachId, modulFachId, "Mathe", "Analysis Grundlagen", "täglich", "user1", karteikarten);

		assertEquals(Optional.of(1).get(), dto.id());
		assertEquals(fachId, dto.fachId());
		assertEquals(modulFachId, dto.modulFachId());
		assertEquals("Mathe", dto.name());
		assertEquals("Analysis Grundlagen", dto.beschreibung());
		assertEquals("täglich", dto.lernIntervalle());
		assertEquals("user1", dto.username());
		assertEquals(karteikarten, dto.karteikarten());
	}

	@Test
	void testEqualsAndHashCode() {
		UUID fachId = UUID.randomUUID();
		UUID modulFachId = UUID.randomUUID();

		StapelDto dto1 = new StapelDto(1, fachId, modulFachId, "Mathe", "Beschreibung", "wöchentlich", "user1", List.of());
		StapelDto dto2 = new StapelDto(1, fachId, modulFachId, "Mathe", "Beschreibung", "wöchentlich", "user1", List.of());

		assertEquals(dto1, dto2);
		assertEquals(dto1.hashCode(), dto2.hashCode());
	}

	@Test
	void testToString() {
		UUID fachId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
		UUID modulFachId = UUID.fromString("123e4567-e89b-12d3-a456-426614174001");

		StapelDto dto = new StapelDto(1, fachId, modulFachId, "Mathe", "Beschreibung", "täglich", "user1", List.of());

		String expected = "StapelDto[id=1, fachId=123e4567-e89b-12d3-a456-426614174000, modulFachId=123e4567-e89b-12d3-a456-426614174001, name=Mathe, beschreibung=Beschreibung, lernIntervalle=täglich, username=user1, karteikarten=[]]";
		assertEquals(expected, dto.toString());
	}
}
