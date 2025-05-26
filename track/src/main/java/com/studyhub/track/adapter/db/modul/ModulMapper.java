package com.studyhub.track.adapter.db.modul;

import com.studyhub.track.domain.model.modul.Modul;
import org.springframework.data.relational.core.sql.In;

public class ModulMapper {
	public static ModulDto toModulDto(Modul modul, Integer existingDbKey) {
		return new ModulDto(existingDbKey,
				modul.getFachId(),
				modul.getName(),
				modul.getSecondsLearned(),
				modul.getKreditpunkte(),
				modul.getUsername(),
				modul.isActive(),
				modul.getSemesterstufe(),
				modul.getSemester(),
				modul.getKlausurDate(),
				modul.getLerntage(),
				modul.getModultermine());
	}

	public static Modul toModul(ModulDto dto) {
		return new Modul(
				dto.fachId(),
				dto.name(),
				dto.secondsLearned(),
				dto.kreditpunkte(),
				dto.username(),
				dto.active(),
				dto.semesterstufe(),
				dto.semester(),
				dto.klausurDate(),
				dto.lerntage(),
				dto.modultermine());
	}

	public static ModulDto toModulDtoNoId(Modul modul) {
		return new ModulDto(null,
				modul.getFachId(),
				modul.getName(),
				modul.getSecondsLearned(),
				modul.getKreditpunkte(),
				modul.getUsername(),
				modul.isActive(),
				modul.getSemesterstufe(),
				modul.getSemester(),
				modul.getKlausurDate(),
				modul.getLerntage(),
				modul.getModultermine());
	}
}
