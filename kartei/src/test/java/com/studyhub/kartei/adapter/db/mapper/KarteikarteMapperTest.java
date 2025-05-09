package com.studyhub.kartei.adapter.db.mapper;

import com.studyhub.kartei.adapter.db.dto.KarteikarteDto;
import com.studyhub.kartei.adapter.db.mapper.KarteikarteMapper;
import com.studyhub.kartei.domain.model.FrageTyp;
import com.studyhub.kartei.domain.model.Karteikarte;
import com.studyhub.kartei.util.KarteikarteMother;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class KarteikarteMapperTest {

	@Test
	@DisplayName("Korrektes Mapping Karteikarte -> KarteikarteDto")
	void test_01() {
		Karteikarte k = KarteikarteMother.newKarteikarte("frage", "antwort");

		KarteikarteDto dto = KarteikarteMapper.toKarteikarteDto(k, 1);

		assertThat(dto.fachId()).isEqualTo(k.getFachId());
		assertThat(dto.frage()).isEqualTo(k.getFrage());
		assertThat(dto.antwort()).isEqualTo(k.getAntwort());
		assertThat(dto.erstelltAm()).isEqualTo(k.getErstelltAm());
		assertThat(dto.letzteAenderungAm()).isEqualTo(k.getLetzteAenderungAm());
		assertThat(dto.faelligAm()).isEqualTo(k.getFaelligAm());
		assertThat(dto.notiz()).isEqualTo(k.getNotiz());
		assertThat(dto.wasHard()).isEqualTo(k.getWasHard());
		assertThat(dto.frageTyp()).isEqualTo(k.getFrageTyp());
		assertThat(dto.antwortzeitSekunden()).isEqualTo(k.getAntwortzeitSekunden());
		assertThat(dto.lernstufen()).isEqualTo(k.getLernstufen());
	}

	@Test
	@DisplayName("Korrektes Mapping KarteikarteDto -> Karteikarte")
	void test_02() {
		LocalDateTime now = LocalDateTime.now();
		KarteikarteDto dto = new KarteikarteDto(1, UUID.randomUUID(), "f", "aw", new ArrayList<>(), now, now, now, "n", 2, FrageTyp.NORMAL, 10, "2d,2d,2d");

		Karteikarte k = KarteikarteMapper.toKarteikarte(dto);

		assertThat(k.getFachId()).isEqualTo(dto.fachId());
		assertThat(k.getFrage()).isEqualTo(dto.frage());
		assertThat(k.getAntwort()).isEqualTo(dto.antwort());
		assertThat(k.getErstelltAm()).isEqualTo(dto.erstelltAm());
		assertThat(k.getLetzteAenderungAm()).isEqualTo(dto.letzteAenderungAm());
		assertThat(k.getFaelligAm()).isEqualTo(dto.faelligAm());
		assertThat(k.getNotiz()).isEqualTo(dto.notiz());
		assertThat(k.getWasHard()).isEqualTo(dto.wasHard());
		assertThat(k.getFrageTyp()).isEqualTo(dto.frageTyp());
		assertThat(k.getAntwortzeitSekunden()).isEqualTo(dto.antwortzeitSekunden());
		assertThat(k.getLernstufen()).isEqualTo(dto.lernstufen());
	}

	@Test
	@DisplayName("Wenn ein Objekt nicht gemapped werden kann, wird eine Exception geworfen")
	void test_3() {
		assertThrows(DtoMappingException.class, () ->
				KarteikarteMapper.toKarteikarte(null)
		);

		assertThrows(DtoMappingException.class, () ->
				KarteikarteMapper.toKarteikarteDto(null, 1)
		);
	}
}
