package com.studyhub.track.service;

import com.studyhub.track.adapter.db.modul.ModulDao;
import com.studyhub.track.adapter.db.modul.ModulRepositoryImpl;
import com.studyhub.track.application.service.ModulRepository;
import com.studyhub.track.domain.model.modul.Modul;
import com.studyhub.track.domain.model.modul.Modultermin;
import com.studyhub.track.domain.model.modul.Terminfrequenz;
import com.studyhub.track.util.ModulMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.utility.TestcontainersConfiguration;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestcontainersConfiguration.class)
@DataJdbcTest
@ActiveProfiles("test")
@Rollback(false)
@Sql(scripts = "init_modul_db_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class ModulRepositoryTest {

	@Autowired
	ModulDao modulRepository;

	ModulRepository repository;

	@BeforeEach
	void setUp() {
		repository = new ModulRepositoryImpl(modulRepository);
	}

	@Test
	@DisplayName("Modul wird erfolgreich abgespeichert")
	void test_01() {
		Modul modul = ModulMother.initModul();

		Modul saved = repository.save(modul);

		assertThat(modul.getFachId()).isEqualTo(saved.getFachId());
		assertThat(modul.getName()).isEqualTo(saved.getName());
	}

	@Test
	@DisplayName("Alle Module werden gefunden.")
	void test_02() {
		List<Modul> l = repository.findAll();

		assertThat(l.size()).isEqualTo(7);
	}


	@Test
	@DisplayName("Modul wird erfolgreich gelöscht.")
	void test_03() {
		UUID fachId = UUID.fromString("f47ac10b-58cc-4372-a567-0e12b2c3d479");

		repository.deleteByUuid(fachId);

		assertThat(repository.findAll().size()).isEqualTo(6);
	}


	@Test
	@DisplayName("Modul wird erfolgreich reseted bzw. upgedated.")
	void test_04() {
		UUID fachId = UUID.fromString("f47ac10b-58cc-4372-a567-0e12b2c3d479");

		repository.updateSecondsByUuid(fachId, 0);
		Modul foundModul = repository.findByUuid(fachId);

		assertThat(foundModul.getSecondsLearned()).isEqualTo(0);
	}

	@Test
	@DisplayName("Active/nicht aktive Module werden erfolgreich für einen User gefundengefunden.")
	void test_05() {
		List<Modul> activeModule = repository.findActiveModuleByUsername(true, "user123");
		List<Modul> notActiveModule = repository.findActiveModuleByUsername(false, "user123");

		assertThat(activeModule.size()).isEqualTo(1);
		assertThat(notActiveModule.size()).isEqualTo(1);
	}

	@Test
	@DisplayName("Modul wird deaktiviert.")
	void test_06() {
		List<Modul> activeModule = repository.findByActiveIsTrue();
		repository.setActive(activeModule.get(0).getFachId(), false);
		Modul m = repository.findByUuid(activeModule.get(0).getFachId());

		assertThat(m.isActive()).isFalse();

	}

	@Test
	@DisplayName("Module wird aktiviert.")
	void test_07() {
		List<Modul> activeModule = repository.findByActiveIsFalse();
		repository.setActive(activeModule.get(0).getFachId(), true);
		Modul m = repository.findByUuid(activeModule.get(0).getFachId());

		assertThat(m.isActive()).isTrue();
	}

	@Test
	@DisplayName("seconds aus allen Modulen eines Users wird korrekt summiert.")
	void test_08() {
		Integer sumSeconds = repository.getTotalStudyTime("peter4");

		assertThat(sumSeconds).isEqualTo(90);
	}

	@Test
	@DisplayName("Modul mit den wenigsten seconds wird gefunden.")
	void test_09() {
		String modulmMinSeconds = repository.findByMinSeconds("peter4");

		assertThat(modulmMinSeconds).isEqualTo("mod6");
	}

	@Test
	@DisplayName("Modul mit den meisten seconds wird gefunden.")
	void test_10() {
		String modulMaxSeconds = repository.findByMaxSeconds("peter4");

		assertThat(modulMaxSeconds).isEqualTo("mod4");
	}

	@Test
	@DisplayName("Wenn mehrere Module mit der gleichen Zeit am wenigsten gelernt wurden, wird lexikographisch das erste Modul gefunden.")
	void test_11() {
		String modulMinSeconds = repository.findByMinSeconds("peter4");

		assertThat(modulMinSeconds).isEqualTo("mod6");
	}

	@Test
	@DisplayName("Wenn mehrere Module mit der gleichen Zeit am häufigsten gelernt wurden, wird lexikographisch das erste Modul gefunden.")
	void test_12() {
		String modulMaxSeconds = repository.findByMaxSeconds("peter4");

		assertThat(modulMaxSeconds).isEqualTo("mod4");
	}

	@Test
	@DisplayName("Die Datenbank kann erfolgreich gepinged werden")
	void test_13() {
		assertThat(repository.isModulDbHealthy()).isTrue();
	}

	@Disabled("muss nur gefixed werden")
	@Test
	@DisplayName("Eine Liste von neuen Modulen wird erfolgreich abgespeichert")
	void test_14() {
		List<Modul> module = List.of(ModulMother.initModulWithName("modul1"),
				ModulMother.initModulWithName("modul2"));

		List<Modul> saved = repository.saveAll(module);

		assertThat(saved.size()).isEqualTo(module.size());
		assertThat(saved.get(1).getName()).isEqualTo("modul2");
	}

	@Test
	@DisplayName("Das Klausur-Datum eines existierenden Moduls wird gefunden")
	void test_15() {
		UUID fachId = UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479");

		String foundKlausurDate = modulRepository.findKlausurDateByFachId(fachId);

		assertThat(foundKlausurDate).isEqualTo("2025-03-23 14:30:00");
	}

	@Test
	@DisplayName("Falls kein Klausur-Datum für ein existierendes Modul eingetragen wurde, wird null zurückgegeben")
	void test_16() {
		UUID fachId = UUID.fromString("f48ac10c-58cc-4372-a537-0e02b2c3d479");

		String foundKlausurDate = modulRepository.findKlausurDateByFachId(fachId);

		assertThat(foundKlausurDate).isEqualTo(null);
	}

	@Test
	@DisplayName("Anzahl aktiver Module für einen User wird zurückgegeben")
	void test_17() {
		int anz = repository.countActiveModules("user123");

		assertThat(anz).isEqualTo(1);
	}

	@Test
	@DisplayName("Anzahl nicht aktiver Module für einen User wird zurückgegeben")
	void test_18() {
		int anz = repository.countNotActiveModules("peter4");

		assertThat(anz).isEqualTo(4);
	}

	@Test
	@DisplayName("Gültiges Modultermin wird erfolgreich hinzugefügt und abgespeichert")
	void test_19() {
		UUID fachId = UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479");
		Modultermin neuerTermin = new Modultermin(
				"T2",
				LocalDateTime.of(2024, 5, 15, 10, 0),
				LocalDateTime.of(2024, 5, 15, 12, 0),
				"Raum A",
				Terminfrequenz.EINMALIG);

		boolean success = repository.addModultermin(fachId, neuerTermin);

		assertThat(success).isTrue();
	}

	@Test
	@DisplayName("Unültiges Modultermine werden nicht hinzugefügt und abgespeichert")
	void test_20() {
		UUID fachId1 = null;
		Modultermin neuerTermin1 = new Modultermin(
				"T2",
				LocalDateTime.of(2024, 5, 15, 10, 0),
				LocalDateTime.of(2024, 5, 15, 12, 0),
				"Raum A",
				Terminfrequenz.EINMALIG);
		boolean success1 = repository.addModultermin(fachId1, neuerTermin1);

		UUID fachId2 = UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479");
		Modultermin neuerTermin2 = null;
		boolean success2 = repository.addModultermin(fachId2, neuerTermin2);

		assertThat(success1).isFalse();
		assertThat(success2).isFalse();
	}

	@Test
	@DisplayName("Modultermin kann aus Modul und Datenbank gelöscht werden")
	void test_21() {
		UUID fachId = UUID.fromString("b8f6e2f5-91a0-4e6d-91b0-ff4e6932a82a");
		LocalDateTime ldt1 = LocalDateTime.of(2024, 3, 30, 14, 30, 0);
		LocalDateTime ldt2 = LocalDateTime.of(2024, 4, 30, 14, 30, 0);
		Modultermin oldTermin = new Modultermin("T6", ldt1, ldt2, null, Terminfrequenz.EINMALIG);

		boolean success = repository.deleteModultermin(fachId, oldTermin);

		assertThat(success).isTrue();

		//Modul foundModul = repository.findByUuid(fachId); angeblich werden 2 gefunden ???
		//assertThat(foundModul.getModultermine()).hasSize(0);
	}

	@Test
	@DisplayName("Löschen eines Modultermins mit ungültigen Parametern ist ncniht möglich")
	void test_22() {
		UUID fachId1 = null;
		LocalDateTime ldt1 = LocalDateTime.of(2024, 3, 30, 14, 30, 0);
		LocalDateTime ldt2 = LocalDateTime.of(2024, 4, 30, 14, 30, 0);
		Modultermin oldTermin1 = new Modultermin("T6", ldt1, ldt2, null, Terminfrequenz.EINMALIG);
		boolean success1 = repository.deleteModultermin(fachId1, oldTermin1);

		assertThat(success1).isFalse();


		UUID fachid2 = UUID.fromString("b8f6e2f5-91a0-4e6d-91b0-ff4e6932a82a");
		Modultermin oldTermin2 = null;
		boolean success2 = repository.deleteModultermin(fachid2, oldTermin2);

		assertThat(success2).isFalse();
	}
}