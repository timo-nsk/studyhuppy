package com.studyhub.track.adapter.db;

import com.studyhub.track.adapter.db.modul.ModulDto;
import com.studyhub.track.adapter.db.modul.ModulMapper;
import com.studyhub.track.domain.model.modul.Modul;
import com.studyhub.track.util.ModulMother;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;

public class ModulMapperTest {

	@Test
	@DisplayName("toModulDto sollte Modul zu ModulDto mappen")
	void test_1() {
		Modul modul = new Modul(UUID.randomUUID(), "Mathematik", 3600, ModulMother.DEFAULT_KREDITPUNKTE, "timo_neske", true, 1, ModulMother.DEFAULT_SEMESTER,  ModulMother.DEFAULT_LERNTAGE, ModulMother.DEFAULT_MODULTERMINE);

		ModulDto dto = ModulMapper.toModulDto(modul,1);

		assertThat(dto).isNotNull();
		assertThat(dto.fachId()).isEqualTo(modul.getFachId());
		assertThat(dto.name()).isEqualTo(modul.getName());
		assertThat(dto.secondsLearned()).isEqualTo(modul.getSecondsLearned());
		assertThat(dto.kreditpunkte().getAnzahlPunkte()).isEqualTo(modul.getKreditpunkte().getAnzahlPunkte());
		assertThat(dto.username()).isEqualTo(modul.getUsername());
		assertThat(dto.active()).isEqualTo(modul.isActive());
	}

	@Test
	@DisplayName("toModul sollte ModulDto zu Modul mappen")
	void test_2() {
		ModulDto dto = new ModulDto(1, UUID.randomUUID(), "Mathematik", 3600, ModulMother.DEFAULT_KREDITPUNKTE, "timo_neske", true, 1, ModulMother.DEFAULT_SEMESTER, ModulMother.DEFAULT_LERNTAGE, ModulMother.DEFAULT_MODULTERMINE);

		Modul modul = ModulMapper.toModul(dto);

		assertThat(modul).isNotNull();
		assertThat(modul.getFachId()).isEqualTo(dto.fachId());
		assertThat(modul.getName()).isEqualTo(dto.name());
		assertThat(modul.getSecondsLearned()).isEqualTo(dto.secondsLearned());
		assertThat(modul.getKreditpunkte().getAnzahlPunkte()).isEqualTo(dto.kreditpunkte().getAnzahlPunkte());
		assertThat(modul.getUsername()).isEqualTo(dto.username());
		assertThat(modul.isActive()).isEqualTo(dto.active());
	}

	@Test
	@DisplayName("toModulDtoNoId sollte Modul zu ModulDto ohne ID mappen")
	void test_3() {
		Modul modul = new Modul(UUID.randomUUID(), "Mathematik", 3600, ModulMother.DEFAULT_KREDITPUNKTE, "timo_neske", true, 1, ModulMother.DEFAULT_SEMESTER,  ModulMother.DEFAULT_LERNTAGE, ModulMother.DEFAULT_MODULTERMINE);

		ModulDto dto = ModulMapper.toModulDtoNoId(modul);

		assertThat(dto).isNotNull();
		assertThat(dto.id()).isNull();
		assertThat(dto.fachId()).isEqualTo(modul.getFachId());
		assertThat(dto.name()).isEqualTo(modul.getName());
		assertThat(dto.secondsLearned()).isEqualTo(modul.getSecondsLearned());
		assertThat(dto.kreditpunkte().getAnzahlPunkte()).isEqualTo(modul.getKreditpunkte().getAnzahlPunkte());
		assertThat(dto.username()).isEqualTo(modul.getUsername());
		assertThat(dto.active()).isEqualTo(modul.isActive());
	}
}
