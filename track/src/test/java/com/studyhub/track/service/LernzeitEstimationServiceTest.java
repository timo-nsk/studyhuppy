package com.studyhub.track.service;

import com.studyhub.track.application.service.LernzeitEstimationService;
import com.studyhub.track.domain.model.modul.Kreditpunkte;
import com.studyhub.track.domain.model.modul.Lerntage;
import com.studyhub.track.domain.model.modul.Modul;
import com.studyhub.track.domain.model.semester.Semester;
import com.studyhub.track.domain.model.semester.SemesterPhase;
import com.studyhub.track.domain.model.semester.SemesterTyp;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class LernzeitEstimationServiceTest {

	static LernzeitEstimationService lernzeitEstimationService;

	@BeforeAll
	static void setUp() {
		lernzeitEstimationService = new LernzeitEstimationService();
	}

	@Test
	@DisplayName("""
			Wenn ein User:
			- bereits 100000 Sekunden gelernt hat und
			- in der Vorlesungsphase
			- an 5 Tagen die Woche ein Modul lernen will,
			- das eine Selbststudiumzeit von 210h besitzt,
			wird die Lernezeit pro Tag korrekt ausgerechnet
			""")
	void test_1() {
		int selbststudiumStunden = 210;
		SemesterPhase phase = SemesterPhase.VORLESUNG;
		LocalDate vorlesungBeginn = LocalDate.now();
		Semester semester = new Semester(1, 1, SemesterTyp.WINTERSEMESTER, vorlesungBeginn, vorlesungBeginn.plusDays(60), null, null);
		Lerntage lerntage = new Lerntage(true, true, true, true, true, false, false, phase);
		Kreditpunkte cp = new Kreditpunkte(10, 90, selbststudiumStunden);
		Modul m = new Modul(UUID.randomUUID(),
				"modul",
				100000,
				cp,
				"user",
				true,
				2,
				semester,
				lerntage,
				null);

		int secondsPerDay = lernzeitEstimationService.getAverageLernzeitPerLerntagForModul(m, m.getLerntage().getSemesterPhase());

		assertThat(secondsPerDay).isEqualTo(16400);
	}

	@Test
	@DisplayName("""
			Wenn ein User:
			- bereits 700000 Sekunden gelernt hat und
			- in der Klausurphase
			- an 5 Tagen die Woche ein Modul lernen will,
			- das eine Selbststudiumzeit von 210h besitzt,
			wird die Lernezeit pro Tag korrekt ausgerechnet
			""")
	void test_2() {
		int selbststudiumStunden = 210;
		SemesterPhase phase = SemesterPhase.KLAUSUR;
		LocalDate vorlesungBeginn = LocalDate.now();
		LocalDate vorlesungEnde = vorlesungBeginn.plusDays(60);
		LocalDateTime klausurDate = LocalDateTime.of(vorlesungEnde.plusDays(14), LocalTime.of(10, 0));
		Semester semester = new Semester(1, 1, SemesterTyp.WINTERSEMESTER, vorlesungBeginn, vorlesungEnde, null, null);
		Lerntage lerntage = new Lerntage(true, true, true, true, true, false, false, phase);
		Kreditpunkte cp = new Kreditpunkte(10, 90, selbststudiumStunden);
		Modul m = new Modul(UUID.randomUUID(),
				"modul",
				700000,
				cp,
				"user",
				true,
				2,
				semester,
				lerntage,
				null);

		int secondsPerDay = lernzeitEstimationService.getAverageLernzeitPerLerntagForModul(m, m.getLerntage().getSemesterPhase());

		assertThat(secondsPerDay).isEqualTo(5600);
	}

	@Test
	@DisplayName("Wenn ein User in der Vorlesungsphase ist aber keine Datumsangaben zur Vorlesungszeit gemacht hat, wird 0 returned")
	void test_3() {
		int selbststudiumStunden = 210;
		SemesterPhase phase = SemesterPhase.VORLESUNG;
		Semester semester = new Semester(1, 1, SemesterTyp.WINTERSEMESTER, null, null, null, null);
		Lerntage lerntage = new Lerntage(true, true, true, true, true, false, false, phase);
		Kreditpunkte cp = new Kreditpunkte(10, 90, selbststudiumStunden);
		Modul m = new Modul(UUID.randomUUID(),
				"modul",
				700000,
				cp,
				"user",
				true,
				2,
				semester,
				lerntage,
				null);

		int secondsPerDay = lernzeitEstimationService.getAverageLernzeitPerLerntagForModul(m, m.getLerntage().getSemesterPhase());

		assertThat(secondsPerDay).isEqualTo(0);
	}

	@Disabled
	@Test
	@DisplayName("Wenn ein User in der Klausurphase ist und kein Klausurdatum angegeben hat, wird 0 returned")
	void test_4() {
		int selbststudiumStunden = 210;
		SemesterPhase phase = SemesterPhase.KLAUSUR;
		Semester semester = new Semester(1, 1, SemesterTyp.WINTERSEMESTER, LocalDate.now(), LocalDate.now().plusDays(60), null, null);
		Lerntage lerntage = new Lerntage(true, true, true, true, true, false, false, phase);
		Kreditpunkte cp = new Kreditpunkte(10, 90, selbststudiumStunden);
		Modul m = new Modul(UUID.randomUUID(),
				"modul",
				700000,
				cp,
				"user",
				true,
				2,
				semester,
				lerntage,
				null);

		int secondsPerDay = lernzeitEstimationService.getAverageLernzeitPerLerntagForModul(m, m.getLerntage().getSemesterPhase());

		assertThat(secondsPerDay).isEqualTo(0);
	}
}
