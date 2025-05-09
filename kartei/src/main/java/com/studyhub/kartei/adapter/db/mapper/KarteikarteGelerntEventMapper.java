package com.studyhub.kartei.adapter.db.mapper;

import com.studyhub.kartei.adapter.db.dto.KarteikarteGelerntEventDto;
import com.studyhub.kartei.domain.model.KarteikarteGelerntEvent;

public class KarteikarteGelerntEventMapper {

	public static KarteikarteGelerntEventDto toKarteikarteGelerntEventDto(KarteikarteGelerntEvent event) {
		if (event == null) throw new DtoMappingException("could not map object %s to %s".formatted("KarteikarteGelerntEvent", "KarteikarteGelerntEventDto"));
		return new KarteikarteGelerntEventDto(null, event.getStapelId(), event.getKarteikarteId(), event.getGelerntAm(), event.getSecondsNeeded());
	}

	public static KarteikarteGelerntEvent toKarteikarteGelerntEvent(KarteikarteGelerntEventDto dto) {
		if (dto == null) throw new DtoMappingException("could not map object %s to %s".formatted("KarteikarteGelerntEventDto", "KarteikarteGelerntEvent"));
		return new KarteikarteGelerntEvent(dto.stapelId(), dto.karteikarteId(), dto.gelerntAm(), dto.secondsNeeded());
	}
}
