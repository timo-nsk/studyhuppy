package com.studyhub.kartei.adapter.db.mapper;

import com.studyhub.kartei.adapter.db.dto.KarteikarteDto;
import com.studyhub.kartei.domain.model.Karteikarte;

public class KarteikarteMapper {

	public static Karteikarte toKarteikarte(KarteikarteDto dto) {
		if (dto == null) throw new DtoMappingException("could not map object %s to %s".formatted("KarteikarteDto", "Karteikarte"));
		Karteikarte k = Karteikarte.builder()
				.fachId(dto.fachId())
				.frage(dto.frage())
				.antwort(dto.antwort())
				.antworten(dto.antworten())
				.erstelltAm(dto.erstelltAm())
				.letzteAenderungAm(dto.letzteAenderungAm())
				.faelligAm(dto.faelligAm())
				.notiz(dto.notiz())
				.wasHard(dto.wasHard())
				.frageTyp(dto.frageTyp())
				.antwortzeitSekunden(dto.antwortzeitSekunden())
				.lernstufen(dto.lernstufen())
				.build();

		return k;
	}

	public static KarteikarteDto toKarteikarteDto(Karteikarte karteikarte, Integer existingKey) {
		if (karteikarte == null) throw new DtoMappingException("could not map object %s to %s".formatted("Karteikarte", "KarteikarteDto"));
		return new KarteikarteDto(
				existingKey,
				karteikarte.getFachId(),
				karteikarte.getFrage(),
				karteikarte.getAntwort(),
				karteikarte.getAntworten(),
				karteikarte.getErstelltAm(),
				karteikarte.getLetzteAenderungAm(),
				karteikarte.getFaelligAm(),
				karteikarte.getNotiz(),
				karteikarte.getWasHard(),
				karteikarte.getFrageTyp(),
				karteikarte.getAntwortzeitSekunden(),
				karteikarte.getLernstufen());
	}
}
