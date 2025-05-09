package com.studyhub.kartei.adapter.db.dto;

import com.studyhub.kartei.domain.model.Antwort;
import com.studyhub.kartei.domain.model.FrageTyp;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Table("karteikarte")
public record KarteikarteDto(
							@Id Integer id,
							UUID fachId,
							String frage,
							String antwort,
							List<Antwort> antworten,
							LocalDateTime erstelltAm,
							LocalDateTime letzteAenderungAm,
							LocalDateTime faelligAm,
							String notiz,
							int wasHard,
							FrageTyp frageTyp,
							int antwortzeitSekunden,
							String lernstufen
) {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		KarteikarteDto that = (KarteikarteDto) obj;
		return Objects.equals(id, that.id) &&
				Objects.equals(fachId, that.fachId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, fachId);
	}
}
