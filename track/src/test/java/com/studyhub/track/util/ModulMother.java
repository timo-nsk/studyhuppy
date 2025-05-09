package com.studyhub.track.util;

import com.studyhub.track.domain.model.modul.Kreditpunkte;
import com.studyhub.track.domain.model.modul.Lerntage;
import com.studyhub.track.domain.model.modul.Modul;
import com.studyhub.track.domain.model.semester.Semester;
import com.studyhub.track.domain.model.semester.SemesterPhase;
import com.studyhub.track.domain.model.semester.SemesterTyp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class ModulMother {

	public static Semester DEFAULT_SEMESTER = new Semester(null, SemesterTyp.WINTERSEMESTER, LocalDate.now(), LocalDate.now().plusMonths(2), LocalDate.now(), LocalDate.now().plusMonths(4));
	public static Kreditpunkte DEFAULT_KREDITPUNKTE = new Kreditpunkte(10, 90, 210);
	public static Lerntage DEFAULT_LERNTAGE = new Lerntage(true, true, true, true, true, false, false, SemesterPhase.VORLESUNG);

	public static Modul initModul() {
		return new Modul(UUID.randomUUID(), "Modul", 1000, DEFAULT_KREDITPUNKTE, "user123",true,1, DEFAULT_SEMESTER, LocalDateTime.now(), DEFAULT_LERNTAGE);
	}

	public static Modul initModulWithName(String name) {
		return new Modul(UUID.randomUUID(), name, 1000, DEFAULT_KREDITPUNKTE,  "user123",true,1, DEFAULT_SEMESTER, LocalDateTime.now(), DEFAULT_LERNTAGE);
	}

	public static Modul initModulWithCp(Kreditpunkte kreditpunkte) {
		return new Modul(UUID.randomUUID(), "Modul", 1000, kreditpunkte, "user123",true, 1, DEFAULT_SEMESTER, LocalDateTime.now(), DEFAULT_LERNTAGE);
	}

	public static Modul initModulWithCpAndSecondsLearned(Kreditpunkte kreditpunkte, int secondLearned) {
		return new Modul(UUID.randomUUID(), "Modul", secondLearned, kreditpunkte, "user123",true,1 , DEFAULT_SEMESTER, LocalDateTime.now(), DEFAULT_LERNTAGE);
	}

	public static Modul initModulWithNameAndUsername(String name, String username) {
		return new Modul(UUID.randomUUID(), name, 1000, DEFAULT_KREDITPUNKTE,  username,true,1 , DEFAULT_SEMESTER, LocalDateTime.now(), DEFAULT_LERNTAGE);
	}

}
