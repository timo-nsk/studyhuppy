package com.studyhub.kartei.adapter.db.dto;

import com.studyhub.kartei.adapter.db.dto.KarteikarteDto;
import com.studyhub.kartei.adapter.db.mapper.DtoMappingException;
import com.studyhub.kartei.adapter.db.mapper.KarteikarteGelerntEventMapper;
import com.studyhub.kartei.domain.model.FrageTyp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class KarteikarteDtoTest {

	@Test
	@DisplayName("Instanz wird erfolgreich erstellt")
	void test_1() {
		Integer id = 1;
		UUID fachId = UUID.randomUUID();
		String frage = "Was ist Java?";
		String antwort = "Eine Programmiersprache";
		LocalDateTime erstelltAm = LocalDateTime.now();
		LocalDateTime letzteAenderungAm = LocalDateTime.now();
		LocalDateTime faelligAm = LocalDateTime.now().plusDays(1);
		String notiz = "Wichtige Frage";
		int wasHard = 2;
		FrageTyp frageTyp = FrageTyp.MULTIPLE_CHOICE;
		int antwortzeitSekunden = 30;
		String lernstufen = "10m,4h,2d";

		KarteikarteDto karte = new KarteikarteDto(id, fachId, frage, antwort, new ArrayList<>(), erstelltAm, letzteAenderungAm, faelligAm, notiz, wasHard, frageTyp, antwortzeitSekunden, lernstufen);

		assertThat(karte).isNotNull();
		assertThat(karte.id()).isEqualTo(id);
		assertThat(karte.fachId()).isEqualTo(fachId);
		assertThat(karte.frage()).isEqualTo(frage);
		assertThat(karte.antwort()).isEqualTo(antwort);
		assertThat(karte.erstelltAm()).isEqualTo(erstelltAm);
		assertThat(karte.letzteAenderungAm()).isEqualTo(letzteAenderungAm);
		assertThat(karte.faelligAm()).isEqualTo(faelligAm);
		assertThat(karte.notiz()).isEqualTo(notiz);
		assertThat(karte.wasHard()).isEqualTo(wasHard);
		assertThat(karte.frageTyp()).isEqualTo(frageTyp);
		assertThat(karte.antwortzeitSekunden()).isEqualTo(antwortzeitSekunden);
		assertThat(karte.lernstufen()).isEqualTo(lernstufen);
	}

	@Test
	@DisplayName("Zwei KarteikarteDto sind dann identisch, wenn sie die selbe id und fachId haben und der Hash gleich ist.")
	void test_2() {
		UUID fachId = UUID.randomUUID();
		KarteikarteDto karte1 = new KarteikarteDto(1, fachId, "Frage?", "Antwort", new ArrayList<>(), LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(), "Notiz", 2, FrageTyp.NORMAL, 30, "10m,4h,2d");
		KarteikarteDto karte2 = new KarteikarteDto(1, fachId, "Frage?", "Antwort", new ArrayList<>(), LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(), "Notiz", 2, FrageTyp.NORMAL, 30, "10m,4h,2d");

		assertThat(karte1).isEqualTo(karte2);
		assertThat(karte1.hashCode()).isEqualTo(karte2.hashCode());
	}

	@Test
	void test_03() {
		KarteikarteDto karte = new KarteikarteDto(1, UUID.fromString("123e4567-e89b-12d3-a456-556642440000"), "Frage?", "Antwort", new ArrayList<>(), LocalDateTime.of(2023, 10, 10, 12, 0), LocalDateTime.of(2023, 10, 11, 12, 0), LocalDateTime.of(2023, 10, 12, 12, 0), "Notiz", 2, FrageTyp.NORMAL, 30, "10m,4h,2d");

		assertThat(karte.toString()).contains("Frage?", "Antwort", "10m,4h,2d");
	}

	@Test
	@DisplayName("Wenn ein Objekt nicht gemapped werden kann, wird eine Exception geworfen")
	void test_4() {
		assertThrows(DtoMappingException.class, () ->
				KarteikarteGelerntEventMapper.toKarteikarteGelerntEventDto(null)
		);

		assertThrows(DtoMappingException.class, () ->
				KarteikarteGelerntEventMapper.toKarteikarteGelerntEvent(null)
		);
	}
}

