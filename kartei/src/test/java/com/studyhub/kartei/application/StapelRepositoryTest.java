package com.studyhub.kartei.application;

import com.studyhub.kartei.adapter.db.StapelDao;
import com.studyhub.kartei.adapter.db.StapelRepositoryImpl;
import com.studyhub.kartei.domain.model.Karteikarte;
import com.studyhub.kartei.domain.model.Stapel;
import com.studyhub.kartei.service.application.StapelRepository;
import com.studyhub.kartei.service.application.StapelService;
import com.studyhub.kartei.service.application.lernzeit.KarteikarteGelerntEventRepository;
import com.studyhub.kartei.service.application.lernzeit.LernzeitService;
import com.studyhub.kartei.util.StapelMother;
import org.junit.jupiter.api.BeforeEach;
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

import java.util.List;
import java.util.UUID;

import static com.studyhub.kartei.util.KarteikarteMother.newKarteikarte;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestcontainersConfiguration.class)
@DataJdbcTest
@ActiveProfiles("test")
@Rollback(false)
@Sql(scripts = "drop_stapel.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "init_stapel_db.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class StapelRepositoryTest {

	@Autowired
	StapelDao dao;

	StapelRepository stapelRepository;


	@BeforeEach
	void init() {
		stapelRepository = new StapelRepositoryImpl(dao);
	}

	@Test
	@DisplayName("Ein neur Stapel wird erfolgreich abgespeichert")
	void test_01() {
		Stapel s = StapelMother.initSet();

		Stapel saved = stapelRepository.save(s);

		assertThat(saved).isEqualTo(s);
	}

	@Test
	@DisplayName("Stapel werden gefunden")
	void test_02() {
		Stapel s = StapelMother.initSet();
		stapelRepository.save(s);

		Stapel found = stapelRepository.findByFachId(s.getFachId());

		assertThat(found.getFachId()).isEqualTo(s.getFachId());
	}

	@Test
	@DisplayName("Einem Stapel kann nachträglich eine Karteikarte hinzugefügt werden")
	void test_03() {
		Karteikarte neueKarteikarte = newKarteikarte("Neu hier?", "Ja");
		Stapel s = StapelMother.initSet();
		stapelRepository.save(s);
		Stapel found = stapelRepository.findByFachId(s.getFachId());
		found.addKarteikarte(neueKarteikarte);

		Stapel saved = stapelRepository.save(found);

		assertThat(saved.getKarteikarten().size()).isEqualTo(2);
	}

	@Test
	@DisplayName("Ein Karteikartenset wird erfolgreich anhand seiner Fach-Id gefunden")
	void test_04() {
		Stapel s = StapelMother.initSet();
		UUID savedFachId = s.getFachId();
		stapelRepository.save(s);

		Stapel found = stapelRepository.findByFachId(savedFachId);

		assertThat(found).isNotNull();
		assertThat(found.getFachId()).isEqualTo(savedFachId);
	}

	@Test
	@DisplayName("Ein Karteikarte kann aus einem Set anhand seiner Fach-Id entfernt werdenentfern werden")
	void test_05() {
		String setId = "6f8a3d6e-2c4b-4f8e-924b-8e5f9a6d3b1c";
		String karteToDelete = "7f8a3d6e-2c4b-4f8e-924b-8e5f9a6d3b1c";
		Stapel s = StapelMother.initSetWithIds(setId, karteToDelete);
		stapelRepository.save(s);
		StapelService service = new StapelService(stapelRepository, mock(KarteikarteGelerntEventRepository.class), mock(LernzeitService.class));

		Stapel saved = service.deleteKarteikarteByFachId(UUID.fromString(setId), UUID.fromString(karteToDelete));

		assertThat(saved.getKarteikarten().size()).isEqualTo(0);
	}

	@Test
	@DisplayName("Wenn ein Stapel geladen wird und eine neue Karteikarte hinzugefügt wird, soll kein neues Set angelegt werden")
	@Sql("drop_stapel.sql")
	void test_08() {
		Stapel set = StapelMother.initSet();

		stapelRepository.save(set);
		Stapel loadedSet = stapelRepository.findByFachId(set.getFachId());
		loadedSet.addKarteikarte(newKarteikarte("frage", "antwort"));
		stapelRepository.save(loadedSet);

		int count = stapelRepository.countAll();
		assertThat(count).isNotEqualTo(2);
		assertThat(count).isEqualTo(1);
	}

	@Test
	@DisplayName("Ein Stapel wird anhand seiner Fach-Id erfolgreich gelöscht")
	void test_9() {
		String setToRemove = "6f8a3d6e-2c4c-4f8e-924b-8e5f9a6d3b1c";

		stapelRepository.deleteKarteiSet(setToRemove);

		List<Stapel> l = stapelRepository.findAll();
		assertThat(l.size()).isEqualTo(8); // 9 sind in init_stapel_db.sql
		assertThat(l.stream().anyMatch(e -> e.getFachId().toString().equals(setToRemove))).isFalse();
	}

	@Test
	@DisplayName("Der Name eines Sets wird erfolgreich geändert")
	void test_10() {
		String setToChange = "6f8a3d6e-2c4c-4f8e-924b-8e5f9a6d3b1c";
		String newSetName = "newName";

		stapelRepository.changeSetName(setToChange, newSetName);

		Stapel found = stapelRepository.findByFachId(UUID.fromString(setToChange));
		assertThat(found.getName()).isEqualTo(newSetName);
	}


	@Test
	@DisplayName("Alle Karteikarten in einem Set werden entfernt")
	void test_11() {
		String setToReset = "6f8a3d6e-2c4b-4f8e-924b-8e5f9a6d3b1c";

		stapelRepository.deleteAllKarteikartenOfSet(setToReset);

		Stapel set = stapelRepository.findByFachId(UUID.fromString(setToReset));
		assertThat(set.getKarteikarten().isEmpty()).isTrue();
	}

	@Test
	@DisplayName("Das lern_intervalle-Feld wird erfolgreich aktualisiert.")
	void test_12() {
		String stapelToUpdate = "6f8a3d6e-2c4b-4f8e-924b-8e5f9a6d3b1c";
		String newLernIntervalle = "2m,4h,2d";

		stapelRepository.updateLernIntervalle(newLernIntervalle, UUID.fromString(stapelToUpdate));

		Stapel set = stapelRepository.findByFachId(UUID.fromString(stapelToUpdate));
		assertThat(set.getLernIntervalle()).isEqualTo(newLernIntervalle);
		assertThat(set.getLernIntervalle()).isNotEqualTo("3m,4h,2d");
	}

	@Test
	@DisplayName("Eine Karteikarte wird einem schon existierenden Set hinzugefügt und erfolgreich abgespeichert")
	void test_13() {
		String stapelToUpdate = "6f8a3d6e-2c4b-4f8e-924b-8e5f9a6d3b1c";
		Karteikarte newKarte = newKarteikarte("frage2", "antwort2");

		stapelRepository.updateSetWithNewKarteikarte(stapelToUpdate, newKarte);

		Stapel found = stapelRepository.findByFachId(UUID.fromString(stapelToUpdate));
		Karteikarte neededKarte = found.findKarteikarteByFachId(newKarte.getFachId().toString());
		assertThat(found.getLernIntervalle()).isEqualTo(neededKarte.getLernstufen());
	}

	@Test
	@DisplayName("Ein Stapel mit einer Karte, die 4 Antworten enthält, wird erfolgreich abgespeichert")
	void test_14() {
		Stapel stapel = StapelMother.initStapelWithNAnworten(4);

		Stapel saved = stapelRepository.save(stapel);

		assertThat(saved.getKarteikarten().get(0).getAntworten().size()).isEqualTo(4);
	}

	@Test
	@DisplayName("Alle Stapel für einer User mit username 'peter987' werden gefunden")
	void test_15() {
		List<Stapel> res = stapelRepository.findByUsername("peter987");

		assertThat(res.size()).isEqualTo(4);
	}

	@Test
	@DisplayName("Datenbank wird erfolgreich gepinged")
	void test_16() {
		assertThat(stapelRepository.isStapelDbHealthy()).isTrue();
	}
}

