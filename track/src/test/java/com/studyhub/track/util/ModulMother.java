package com.studyhub.track.util;

import com.studyhub.track.domain.model.modul.Kreditpunkte;
import com.studyhub.track.domain.model.modul.Lerntage;
import com.studyhub.track.domain.model.modul.Modul;
import com.studyhub.track.domain.model.semester.Semester;
import com.studyhub.track.domain.model.semester.SemesterPhase;
import com.studyhub.track.domain.model.semester.SemesterTyp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ModulMother {

	public static Semester DEFAULT_SEMESTER = new Semester(null, 1,  SemesterTyp.WINTERSEMESTER, LocalDate.now(), LocalDate.now().plusMonths(2), LocalDate.now(), LocalDate.now().plusMonths(4));
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

	public static List<Modul> modulListWithSemester() {
		Modul m1 = new Modul(UUID.randomUUID(), "m1", 1000, DEFAULT_KREDITPUNKTE, "peter", true, 3, DEFAULT_SEMESTER, LocalDateTime.now(), DEFAULT_LERNTAGE);
		Modul m2 = new Modul(UUID.randomUUID(), "m2", 2000, DEFAULT_KREDITPUNKTE, "peter", true, 3, DEFAULT_SEMESTER, LocalDateTime.now(), DEFAULT_LERNTAGE);

		Modul m3 = new Modul(UUID.randomUUID(), "m3", 1000, DEFAULT_KREDITPUNKTE, "peter", true, 4, DEFAULT_SEMESTER, LocalDateTime.now(), DEFAULT_LERNTAGE);

		Modul m4 = new Modul(UUID.randomUUID(), "m4", 500, DEFAULT_KREDITPUNKTE, "peter", true, 5, DEFAULT_SEMESTER, LocalDateTime.now(), DEFAULT_LERNTAGE);
		Modul m5 = new Modul(UUID.randomUUID(), "m5", 500, DEFAULT_KREDITPUNKTE, "peter", true, 5, DEFAULT_SEMESTER, LocalDateTime.now(), DEFAULT_LERNTAGE);
		Modul m6 = new Modul(UUID.randomUUID(), "m6", 4000, DEFAULT_KREDITPUNKTE, "peter", true, 5, DEFAULT_SEMESTER, LocalDateTime.now(), DEFAULT_LERNTAGE);
		return List.of(m1, m2, m3, m4, m5, m6);
	}

}
