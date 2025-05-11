package com.studyhub.track.model;

import com.studyhub.track.domain.model.modul.Kreditpunkte;
import com.studyhub.track.domain.model.modul.Modul;
import com.studyhub.track.domain.model.semester.Semester;
import com.studyhub.track.domain.model.semester.SemesterTyp;
import com.studyhub.track.util.ModulMother;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class ModulTest {

	@Test
	@DisplayName("Modul mit 10CP , 90 Kontaktzeitstunden und 210 Selbststudiumstunden hat einen Arbeitsauffwand von 300")
	void test_1() {
		Kreditpunkte kreditpunkte = new Kreditpunkte(10, 90, 210);
		Modul m = ModulMother.initModulWithCp(kreditpunkte);

		assertEquals("300", m.getGesamtArbeitsaufwand());
	}

	@Test
	@DisplayName("Ein Modul, dass den voraussichtlichen Gesamtarbeitsaufwand überschreitet wird korrekt als true gewertet")
	void test_2() {
		Kreditpunkte kreditpunkte = new Kreditpunkte(10, 90, 210);
		Modul m = ModulMother.initModulWithCpAndSecondsLearned(kreditpunkte, 1083600);

		assertTrue(m.überschreitetGesamtarbeitsaufwand());
	}

	@Test
	@DisplayName("Ein Modul, dass den voraussichtlichen Gesamtarbeitsaufwand noch nicht erreicht hat, wird als false gewertet")
	void test_3() {
		Kreditpunkte kreditpunkte = new Kreditpunkte(10, 90, 210);
		Modul m = ModulMother.initModulWithCpAndSecondsLearned(kreditpunkte,  1076400);

		assertFalse(m.überschreitetGesamtarbeitsaufwand());
	}

	@Test
	@DisplayName("Ein Modul, dass den voraussichtlichen Selbststudiumaufwand überschreitet, wird korrekt als true gewertet")
	void test_4() {
		Kreditpunkte kreditpunkte = new Kreditpunkte(10, 90, 210);
		Modul m = ModulMother.initModulWithCpAndSecondsLearned(kreditpunkte,  757000);

		assertTrue(m.überschreitetSelbststudiumaufwand());
	}

	@Test
	@DisplayName("Ein Modul, dass den voraussichtlichen Selbststudiumaufwand noch nicht erreicht hat, wird als false gewertet")
	void test_5() {
		Kreditpunkte kreditpunkte = new Kreditpunkte(10, 90, 210);
		Modul m = ModulMother.initModulWithCpAndSecondsLearned(kreditpunkte,  755000);

		assertFalse(m.überschreitetSelbststudiumaufwand());
	}

	@Test
	@DisplayName("Wenn ein Modul kein Datum für eine Klausur eingetragen hat, wird false zurückgegeben")
	void test_6() {
		Modul m = new Modul(UUID.randomUUID(), "modul1",
				10000,
				null,
				"peter44",
				true,
				2,
				null,
				null,
				null);

		boolean klausurEingetragen = m.klausurDatumEingetragen();

		assertFalse(klausurEingetragen);
	}

	@Test
	@DisplayName("Wenn ein Modul ein Datum für eine Klausur eingetragen hat, wird true zurückgegeben")
	void test_7() {
		Modul m = new Modul(UUID.randomUUID(), "modul1",
				10000,
				null,
				"peter44",
				true,
				2,
				null,
				LocalDateTime.MAX,
				null);

		boolean klausurEingetragen = m.klausurDatumEingetragen();

		assertTrue(klausurEingetragen);
	}

	@Test
	@DisplayName("Wenn das Semester, in dem das Modul stattfindet keine Datum-Angaben für den Vorlesungsbeginn und -ende hat wird false zurückgegeben")
	void test_8() {
		Semester s = new Semester(1, 1, SemesterTyp.WINTERSEMESTER, null, null, null, null);
		Modul m = new Modul(UUID.randomUUID(), "modul1",
				10000,
				null,
				"peter44",
				true,
				2,
				s,
				null,
				null);

		boolean vorlesungEingetragen = m.vorlesungDatumangabenEingetragen();

		assertFalse(vorlesungEingetragen);
	}

	@Test
	@DisplayName("Wenn das Semester, in dem das Modul stattfindet keine Datum-Angaben für den Vorlesungsbeginn hat wird false zurückgegeben")
	void test_9() {
		Semester s = new Semester(1, 1, SemesterTyp.WINTERSEMESTER, LocalDate.now(), null, null, null);
		Modul m = new Modul(UUID.randomUUID(), "modul1",
				10000,
				null,
				"peter44",
				true,
				2,
				s,
				null,
				null);

		boolean vorlesungEingetragen = m.vorlesungDatumangabenEingetragen();

		assertFalse(vorlesungEingetragen);
	}

	@Test
	@DisplayName("Wenn das Semester, in dem das Modul stattfindet keine Datum-Angaben für das Vorlesungsende hat wird false zurückgegeben")
	void test_10() {
		Semester s = new Semester(1, 1, SemesterTyp.WINTERSEMESTER, null, LocalDate.now(), null, null);
		Modul m = new Modul(UUID.randomUUID(), "modul1",
				10000,
				null,
				"peter44",
				true,
				2,
				s,
				null,
				null);

		boolean vorlesungEingetragen = m.vorlesungDatumangabenEingetragen();

		assertFalse(vorlesungEingetragen);
	}

	@Test
	@DisplayName("Wenn das Semester, in dem das Modul stattfindet eine Datum-Angaben für den Vorlesungsbeginn und das Vorlesungende hat wird true zurückgegeben")
	void test_11() {
		Semester s = new Semester(1, 1, SemesterTyp.WINTERSEMESTER, LocalDate.now(), LocalDate.now(), null, null);
		Modul m = new Modul(UUID.randomUUID(), "modul1",
				10000,
				null,
				"peter44",
				true,
				2,
				s,
				null,
				null);

		boolean vorlesungEingetragen = m.vorlesungDatumangabenEingetragen();

		assertTrue(vorlesungEingetragen);
	}

	@Test
	@DisplayName("Das übrige benötigt Selbststudium-Zeit wird korrekt berechnet")
	void test_12() {
		Kreditpunkte kp = new Kreditpunkte(10, 10, 10);
		Modul m = new Modul(UUID.randomUUID(), "modul1",
				10000,
				kp,
				"peter44",
				true,
				2,
				null,
				null,
				null);

		int remaining = m.getRemainingSelbststudiumZeit();

		assertThat(remaining).isEqualTo(26000);
	}
}
