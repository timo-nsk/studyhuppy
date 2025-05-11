package com.studyhub.track.service;

import com.studyhub.track.adapter.db.modul.ModulDao;
import com.studyhub.track.adapter.db.modul.ModulRepositoryImpl;
import com.studyhub.track.application.service.ModulRepository;
import com.studyhub.track.domain.model.modul.Modul;
import com.studyhub.track.util.ModulMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.utility.TestcontainersConfiguration;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestcontainersConfiguration.class)
@DataJdbcTest
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
	@Sql("findall.sql")
	@DisplayName("Alle Module werden gefunden.")
	void test_02() {
		List<Modul> l = repository.findAll();

		assertThat(l.size()).isEqualTo(5);
	}


	@Test
	@Sql("findall.sql")
	@DisplayName("Modul wird erfolgreich gelöscht.")
	void test_03() {
		UUID fachId = UUID.fromString("f47ac10b-58cc-4372-a567-0e12b2c3d479");

		repository.deleteByUuid(fachId);

		assertThat(repository.findAll().size()).isEqualTo(4);
	}


	@Test
	@Sql("findall.sql")
	@DisplayName("Modul wird erfolgreich reseted bzw. upgedated.")
	void test_04() {
		UUID fachId = UUID.fromString("f47ac10b-58cc-4372-a567-0e12b2c3d479");

		repository.updateSecondsByUuid(fachId, 0);
		Modul foundModul = repository.findByUuid(fachId);

		assertThat(foundModul.getSecondsLearned()).isEqualTo(0);
	}

	@Test
	@Sql("findall.sql")
	@DisplayName("Active/nicht aktive Module werden erfolgreich für einen User gefundengefunden.")
	void test_05() {
		List<Modul> activeModule = repository.findActiveModuleByUsername(true, "user123");
		List<Modul> notActiveModule = repository.findActiveModuleByUsername(false, "user123");

		assertThat(activeModule.size()).isEqualTo(1);
		assertThat(notActiveModule.size()).isEqualTo(1);
	}

	@Test
	@Sql("findall.sql")
	@DisplayName("Modul wird deaktiviert.")
	void test_06() {
		List<Modul> activeModule = repository.findByActiveIsTrue();
		repository.setActive(activeModule.get(0).getFachId(), false);
		Modul m = repository.findByUuid(activeModule.get(0).getFachId());

		assertThat(m.isActive()).isFalse();

	}

	@Test
	@Sql("findall.sql")
	@DisplayName("Module wird aktiviert.")
	void test_07() {
		List<Modul> activeModule = repository.findByActiveIsFalse();
		repository.setActive(activeModule.get(0).getFachId(), true);
		Modul m = repository.findByUuid(activeModule.get(0).getFachId());

		assertThat(m.isActive()).isTrue();
	}

	@Test
	@Sql("findall.sql")
	@DisplayName("seconds aus allen Modulen eines Users wird korrekt summiert.")
	void test_08() {
		Integer sumSeconds = repository.getTotalStudyTime("peter4");

		assertThat(sumSeconds).isEqualTo(80);
	}

	@Test
	@Sql("findall.sql")
	@DisplayName("Modul mit den wenigsten seconds wird gefunden.")
	void test_09() {
		String modulmMinSeconds = repository.findByMinSeconds();

		assertThat(modulmMinSeconds).isEqualTo("mod1");
	}

	@Test
	@Sql("findall.sql")
	@DisplayName("Modul mit den meisten seconds wird gefunden.")
	void test_10() {
		String modulMaxSeconds = repository.findByMaxSeconds();

		assertThat(modulMaxSeconds).isEqualTo("mod4");
	}

	@Test
	@Sql("findall.sql")
	@DisplayName("Wenn mehrere Module mit der gleichen Zeit am wenigsten gelernt wurden, wird lexikographisch das erste Modul gefunden.")
	void test_11() {
		String modulMinSeconds = repository.findByMinSeconds();

		assertThat(modulMinSeconds).isEqualTo("mod1");
	}

	@Test
	@Sql("findall.sql")
	@DisplayName("Wenn mehrere Module mit der gleichen Zeit am häufigsten gelernt wurden, wird lexikographisch das erste Modul gefunden.")
	void test_12() {
		String modulMaxSeconds = repository.findByMaxSeconds();

		assertThat(modulMaxSeconds).isEqualTo("mod4");
	}

	@Test
	@DisplayName("Die Datenbank kann erfolgreich gepinged werden")
	void test_13() {
		assertThat(repository.isModulDbHealthy()).isTrue();
	}

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
	@Sql("findall.sql")
	void test_15() {
		UUID fachId = UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479");

		String foundKlausurDate = modulRepository.findKlausurDateByFachId(fachId);

		assertThat(foundKlausurDate).isEqualTo("2025-03-23 14:30:00");
	}

	@Test
	@DisplayName("Falls kein Klausur-Datum für ein existierendes Modul eingetragen wurde, wird null zurückgegeben")
	@Sql("findall.sql")
	void test_16() {
		UUID fachId = UUID.fromString("f48ac10c-58cc-4372-a537-0e02b2c3d479");

		String foundKlausurDate = modulRepository.findKlausurDateByFachId(fachId);

		assertThat(foundKlausurDate).isEqualTo(null);
	}

	@Test
	@DisplayName("Anzahl aktiver Module für einen User wird zurückgegeben")
	@Sql("findall.sql")
	void test_17() {
		int anz = repository.countActiveModules("user123");

		assertThat(anz).isEqualTo(1);
	}

	@Test
	@DisplayName("Anzahl nicht aktiver Module für einen User wird zurückgegeben")
	@Sql("findall.sql")
	void test_18() {
		int anz = repository.countNotActiveModules("peter4");

		assertThat(anz).isEqualTo(3);
	}

}