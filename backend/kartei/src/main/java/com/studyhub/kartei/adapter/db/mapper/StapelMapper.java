package com.studyhub.kartei.adapter.db.mapper;

import com.studyhub.kartei.adapter.db.dto.StapelDto;
import com.studyhub.kartei.domain.model.Stapel;

public class StapelMapper {

	public static Stapel toStapel(StapelDto dto) {
		if (dto == null) throw new DtoMappingException("could not map object %s to %s".formatted("StapelDto", "Stapel"));
		return new Stapel(dto.fachId(), dto.modulFachId(), dto.name(), dto.beschreibung(), dto.lernIntervalle(), dto.username(), dto.karteikarten());
	}

	public static StapelDto toStapelDto(Integer existingKey, Stapel set) {
		if (set == null) throw new DtoMappingException("could not map object %s to %s".formatted("Stapel", "StapelDto"));
		return new StapelDto(existingKey, set.getFachId(), set.getModulFachId(), set.getName(), set.getBeschreibung(), set.getLernIntervalle(), set.getUsername(), set.getKarteikarten());
	}
}
