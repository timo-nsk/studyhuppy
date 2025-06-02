package com.studyhub.track.service;

import com.studyhub.track.application.JWTService;
import com.studyhub.track.application.service.ModulRepository;
import com.studyhub.track.application.service.ModulService;
import com.studyhub.track.application.service.TimeConverter;
import com.studyhub.track.domain.model.modul.Modul;
import com.studyhub.track.util.ModulMother;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.*;

import static com.studyhub.track.util.ModulMother.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class ModulServiceTest {

	static ModulRepository repo;
	static ModulService modulService;
	static JWTService jwtService;

	@BeforeAll
	static void init() {
		repo = mock(ModulRepository.class);
		jwtService = mock(JWTService.class);
		modulService = new ModulService(repo, jwtService);
	}

	@Test
	@DisplayName("repo.save() wird korrekt aufgerufen")
	void test_01() {
		modulService.saveNewModul(initModul());
		verify(repo).save(any(Modul.class));
	}

	@Test
	@DisplayName("repo.findAll() wird korrekt aufgerufen")
	void test_02() {
		modulService.findAll();
		verify(repo).findAll();
	}

	@Test
	@DisplayName("repo.updateSecondsByUuid() wird aufgerufen und Metode aktualisiert erfolgreich")
	void test_03() throws Exception {
		UUID uuid = UUID.randomUUID();
		when(repo.updateSecondsByUuid(uuid, 10)).thenReturn(1);
		modulService.updateSeconds(uuid, 10);
		verify(repo).updateSecondsByUuid(any(UUID.class), anyInt());
	}

	@Test
	@DisplayName("repo.deleteByUuid() wird korrekt aufgerufen")
	void test_04() {
		modulService.deleteModul(UUID.randomUUID());
		verify(repo).deleteByUuid(any(UUID.class));
	}

	@Test
	@DisplayName("repo.findActiveModuleByUsername() wird korrekt aufgerufen")
	void test_05() {
		modulService.findActiveModuleByUsername(true, "user123");
		verify(repo).findActiveModuleByUsername(true, "user123");
	}

	@Test
	@DisplayName("addTime funktioniert korrekt und addiert die Sekunden zum Modul")
	void test_08() throws Exception {
		UUID modulId = UUID.randomUUID();
		String time = "00:01";
		TimeConverter tc = mock(TimeConverter.class);
		when(tc.timeToSeconds(time)).thenReturn(60);
		when(repo.findSecondsById(any())).thenReturn(60);
		when(repo.updateSecondsByUuid(modulId, 120)).thenReturn(1);

		modulService.addTime(modulId, time);

		verify(repo).updateSecondsByUuid(modulId, 120);
	}

	@Test
	@DisplayName("Finde alle Module und transformiere die Liste in eine Map<Fach-Id, Modulname>")
	void tst_09() {
		List<Modul> modulList = new ArrayList<>();
		modulList.add(ModulMother.initModulWithName("Data Science"));
		modulList.add(ModulMother.initModulWithName("Aldat"));
		Map<UUID, String> desiredMap = new HashMap<>();
		desiredMap.put(modulList.get(0).getFachId(), modulList.get(0).getName());
		desiredMap.put(modulList.get(1).getFachId(), modulList.get(1).getName());
		when(repo.findAll()).thenReturn(modulList);

		//TODO: fix
		//Map<UUID, String> returnedMap = modulService.getModuleMap();

		//assertThat(returnedMap).isEqualTo(desiredMap);
	}

	@Test
	@DisplayName("Englisches Datums-Format wird korrekt ins deutsche Format geparsed")
	void test_10() {
		String eng = "2025-03-27 10:00:00";

		String ger = modulService.dateStringGer(eng);

		assertThat(ger).isEqualTo("27.03.2025, 10:00");
	}

	@Test
	@DisplayName("Das Klausur-Datum eines Moduls wird aus der Datenbank geholt und korrekt in das deutsche Format geparsed")
	void test_11() {
		UUID fachId = UUID.randomUUID();
		String eng = "2025-03-27 10:00:00";
		when(repo.findKlausurDateByFachId(any(UUID.class))).thenReturn(eng);

		String ger = modulService.findKlausurDateByFachId(fachId);

		assertThat(ger).isEqualTo("27.03.2025, 10:00");
	}

	@Test
	@DisplayName("Die Tage bis zur Klausur werden korrekt berechnet")
	void test_12() {
		LocalDateTime klausur = LocalDateTime.of(2025, 5,10, 10, 0);
		LocalDateTime today = LocalDateTime.of(2025, 5,1, 10, 0);

		int diff = modulService.computeDayDifference(today, klausur);

		assertThat(diff).isEqualTo(9);
	}

	@Test
	@DisplayName("Die Tage bis zur Klausur über Monatsgrenzen werden korrekt berechnet")
	void test_13() {
		LocalDateTime klausur = LocalDateTime.of(2025, 5,10, 10, 0);
		LocalDateTime today = LocalDateTime.of(2025, 2,10, 10, 0);

		int diff = modulService.computeDayDifference(today, klausur);

		assertThat(diff).isEqualTo(89);
	}

	@Test
	@DisplayName("Für einen User wird die lernzeit pro Fachsemester als Map berechnet")
	void test_14() {
		List<Modul> modulList = ModulMother.modulListWithSemester();
		Map<Integer, Integer> expectedMap = new HashMap<>();
		expectedMap.put(3, 3000);
		expectedMap.put(4, 1000);
		expectedMap.put(5, 5000);
		when(repo.findByUsername("peter")).thenReturn(modulList);

		Map<Integer, Integer> actual = modulService.getTotalStudyTimePerFachSemester("peter");

		assertThat(actual).isEqualTo(expectedMap);
	}

	@Test
	@DisplayName("Wenn ein User nur 10 Module erstellen darf und aktuelle 9 erstellt hat, kann er ein weiteres Modul erstellen")
	void test_15() {
		List<Modul> modulList = ModulMother.initListWithNEmptyModule(9);
		when(repo.findByUsername("peter")).thenReturn(modulList);

		boolean allowed = modulService.modulCanBeCreated("peter", 10);

		assertThat(allowed).isTrue();
	}

	@Test
	@DisplayName("Wenn ein User nur 10 Module erstellen darf und aktuelle 10 erstellt hat, kann er kein weiteres Modul erstellen")
	void test_16() {
		List<Modul> modulList = ModulMother.initListWithNEmptyModule(10);
		when(repo.findByUsername("peter")).thenReturn(modulList);

		boolean allowed = modulService.modulCanBeCreated("peter", 10);

		assertThat(allowed).isFalse();
	}

	@Test
	@DisplayName("getFachsemesterModuleMap baut die Datenstruktur Map<Integer, List<Modul>> korrekt zusammen")
	void test_17() {
		List<Modul> modulList = ModulMother.modulListWithSemester();
		when(repo.findActiveModuleByUsername(true, "peter")).thenReturn(modulList);
		Map<Integer, List<Modul>> expectedMap = new TreeMap<>();
		List<Modul> l1 = List.of(
				new Modul(UUID.randomUUID(), "m1", 1000, DEFAULT_KREDITPUNKTE, "peter", true, 3, DEFAULT_SEMESTER,  DEFAULT_LERNTAGE, DEFAULT_MODULTERMINE) ,
				new Modul(UUID.randomUUID(), "m2", 2000, DEFAULT_KREDITPUNKTE, "peter", true, 3, DEFAULT_SEMESTER,  DEFAULT_LERNTAGE, DEFAULT_MODULTERMINE));
		expectedMap.put(3, l1);
		List<Modul> l2 = List.of(new Modul(UUID.randomUUID(), "m3", 1000, DEFAULT_KREDITPUNKTE, "peter", true, 4, DEFAULT_SEMESTER,  DEFAULT_LERNTAGE, DEFAULT_MODULTERMINE));
		expectedMap.put(4, l2);
		List<Modul> l3 = List.of(
				new Modul(UUID.randomUUID(), "m4", 500, DEFAULT_KREDITPUNKTE, "peter", true, 5, DEFAULT_SEMESTER,  DEFAULT_LERNTAGE, DEFAULT_MODULTERMINE),
				new Modul(UUID.randomUUID(), "m5", 500, DEFAULT_KREDITPUNKTE, "peter", true, 5, DEFAULT_SEMESTER,  DEFAULT_LERNTAGE, DEFAULT_MODULTERMINE),
				new Modul(UUID.randomUUID(), "m6", 4000, DEFAULT_KREDITPUNKTE, "peter", true, 5, DEFAULT_SEMESTER,  DEFAULT_LERNTAGE, DEFAULT_MODULTERMINE));
		expectedMap.put(5, l3);

		Map<Integer, List<Modul>> actualMap = modulService.getFachsemesterModuleMap("peter");

		assertThat(actualMap.get(3)).hasSize(2);
		assertThat(actualMap.get(3).get(0).getName()).isEqualTo("m1");
		assertThat(actualMap.get(3).get(1).getName()).isEqualTo("m2");
		assertThat(actualMap.get(4)).hasSize(1);
		assertThat(actualMap.get(4).get(0).getName()).isEqualTo("m3");
		assertThat(actualMap.get(5)).hasSize(3);
		assertThat(actualMap.get(5).get(0).getName()).isEqualTo("m4");
		assertThat(actualMap.get(5).get(1).getName()).isEqualTo("m5");
		assertThat(actualMap.get(5).get(2).getName()).isEqualTo("m6");
	}
}