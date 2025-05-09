package com.studyhub.kartei.adapter.db.mapper;

import com.studyhub.kartei.adapter.db.dto.StapelDto;
import com.studyhub.kartei.domain.model.Stapel;
import com.studyhub.kartei.util.StapelMother;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.UUID;

import static com.studyhub.kartei.adapter.db.mapper.StapelMapper.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StapelMapperTest {

	@Test
	@DisplayName("Dto-Objekt wird korrekt in Domainen-Objekt gemapped")
	void test_01() {
		UUID fachId = UUID.randomUUID();
		UUID modulFachId = UUID.randomUUID();
		StapelDto dto = new StapelDto(1, fachId, modulFachId, "a", "a", "10m,10h,10d", "user1", new ArrayList<>());

		Stapel domain = toStapel(dto);

		assertThat(domain.getFachId()).isEqualTo(fachId);
		assertThat(domain.getModulFachId()).isEqualTo(modulFachId);
		assertThat(domain.getName()).isEqualTo("a");
		assertThat(domain.getBeschreibung()).isEqualTo("a");
		assertThat(domain.getLernIntervalle()).isEqualTo("10m,10h,10d");
		assertThat(domain.getUsername()).isEqualTo("user1");
		assertThat(domain.getKarteikarten()).isNotNull();
	}

	@Test
	@DisplayName("Domain-Objekt wird korrekt in Dto-Objekt gemapped")
	void test_02() {
		UUID fachId = UUID.randomUUID();
		UUID modulFachId = UUID.randomUUID();
		Stapel domain = StapelMother.initSet();

		StapelDto dto = toStapelDto(null, domain);


		assertThat(dto.id()).isEqualTo(null);
		assertThat(dto.fachId()).isEqualTo(domain.getFachId());
		assertThat(dto.modulFachId()).isEqualTo(domain.getModulFachId());
		assertThat(dto.name()).isEqualTo("TestSet");
		assertThat(dto.beschreibung()).isEqualTo("TestSet Beschreibung");
		assertThat(dto.lernIntervalle()).isEqualTo("10m,1d");
		assertThat(dto.karteikarten()).isNotNull();
	}

	@Test
	@DisplayName("Wenn ein Objekt nicht gemapped werden kann, wird eine Exception geworfen")
	void test_3() {
		assertThrows(DtoMappingException.class, () ->
				StapelMapper.toStapelDto(1, null)
		);

		assertThrows(DtoMappingException.class, () ->
				StapelMapper.toStapel(null)
		);
	}

}
