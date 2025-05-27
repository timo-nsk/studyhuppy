package com.studyhub.kartei.domain.model;

import com.studyhub.kartei.domain.model.Karteikarte;
import com.studyhub.kartei.domain.model.Stapel;
import com.studyhub.kartei.util.KarteikarteMother;
import com.studyhub.kartei.util.StapelMother;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class StapelTest {

	@Test
	@DisplayName("Ein Karteikarten-Set mit nur fälligen Karten gibt die Anzahl der fälligen Karten korrekt zurück (10)")
	void test_01() {
		Stapel set = StapelMother.initSetWithALotKarteikarten();
		LocalDateTime today = LocalDateTime.of(2025, 1, 25, 10, 0, 0);

		int anzahlFälligeKarten = set.anzahlFälligeKarteikarten(today);

		assertThat(anzahlFälligeKarten).isEqualTo(10);
	}

	@Test
	@DisplayName("Ein Karteikarten-Set mit tatsächlich 5 fälligen Karten gibt diese als  Anzahl der fälligen Karten korrekt zurück")
	void test_02() {
		Stapel set = StapelMother.initSetWithALotKarteikarten();
		LocalDateTime today = LocalDateTime.of(2025, 1, 20, 10, 0, 0);

		int anzahlFälligeKarten = set.anzahlFälligeKarteikarten(today);

		assertThat(anzahlFälligeKarten).isEqualTo(5);
	}

	@Test
	@DisplayName("Ein Karteikarten-Set mit keinen fälligen Karten gibt 0 zurück")
	void test_03() {
		Stapel set = StapelMother.initSetWithALotKarteikarten();
		LocalDateTime today = LocalDateTime.of(2025, 1, 10, 10, 0, 0);

		int anzahlFälligeKarten = set.anzahlFälligeKarteikarten(today);

		assertThat(anzahlFälligeKarten).isEqualTo(0);
	}

	@Test
	@DisplayName("Ein Karteikarten-Set ohne Karteikarten hat keine fälligen Karten")
	void test_04() {
		Stapel set = StapelMother.initSetWithoutKarteikarten();

		int anzahlFälligeKarten = set.anzahlFälligeKarteikarten(LocalDateTime.now());

		assertThat(anzahlFälligeKarten).isEqualTo(0);
	}

	@Disabled("Brauche neuen Algo dafür")
	@Test
	@DisplayName("Ein Karteikarten-Set gibt die Anzahl ihrer neuen Karten zurück")
	void test_05() {
		Stapel set = StapelMother.initSetWithALotNewKarteikarten();

		long anzahlNeueKarten = set.anzahlNeueKarteikarten();

		assertThat(anzahlNeueKarten).isEqualTo(3);
	}

	@Test
	@DisplayName("Ein Karteikarten-Set ohne neue Karten gibt 0 zurück")
	void test_06() {
		Stapel set = StapelMother.initSetWithALotKarteikarten();

		long anzahlNeueKarten = set.anzahlNeueKarteikarten();

		assertThat(anzahlNeueKarten).isEqualTo(0);
	}

	@Test
	@DisplayName("Ein Karteikarten-Set ohne Karteikarten gibt 0 für die Anzahl neuer Karten zurück")
	void test_07() {
		Stapel set = StapelMother.initSetWithoutKarteikarten();

		long anzahlNeueKarten = set.anzahlNeueKarteikarten();

		assertThat(anzahlNeueKarten).isEqualTo(0);
	}

	@Test
	@DisplayName("Ein Set ohne Modul-Fach-Id wird erkannt")
	void test_08() {
		Stapel set = StapelMother.initSetWithoutModulFachId();

		assertThat(set.hasModulFachId()).isFalse();
	}

	@Test
	@DisplayName("Ein Set mit Modul-Fach-Id wird erkannt")
	void test_09() {
		Stapel set = StapelMother.initSet();

		assertThat(set.hasModulFachId()).isTrue();
	}

	@Test
	@DisplayName("Ein Stapel, dass einen leeren Lernstufen-Parameter bekommt, wird mit dem Default-Lernstufen-Wert initialisiert")
	void test_10() {
		String DEFAULT_LEARN_INTERVALL = "5m,15m,30m,2h,1d";
		Stapel stapel1 = new Stapel(UUID.randomUUID(), "Stapel1", "iwas", "user1", null);
		Stapel stapel2 = new Stapel(UUID.randomUUID(), "Stapel1", "iwas", "user1", "");

		assertThat(stapel1.getLernIntervalle()).isEqualTo(DEFAULT_LEARN_INTERVALL);
		assertThat(stapel2.getLernIntervalle()).isEqualTo(DEFAULT_LEARN_INTERVALL);
	}

	@Test
	@DisplayName("Eine Karteikarte kann erfolgreich zu einem Stapel hinzugefügt werden")
	void test_11() {
		Stapel stapel = StapelMother.initSet();
		Karteikarte karte = KarteikarteMother.newKarteikarteWithLevel(0);

		assertThat(stapel.addKarteikarte(karte)).isTrue();
	}

	@Test
	@DisplayName("Aus einem Stapel kann die Fach-Id der ersten Karteikarte in diesem Se")
	void test_12() {
		Stapel stapel = StapelMother.initSet();

		assertThat(stapel.getFirstKarteikarteId()).isNotNull();
	}

	@Test
	@DisplayName("Wenn ein Stapel keine Karteikarten hat, dann kann die Fach-Id der ersten Karteikarte nicht gefunden werden")
	void test_13() {
		Stapel stapel = StapelMother.initSetWithoutKarteikarten();

		assertThat(stapel.getFirstKarteikarteId()).isNull();
	}

	@Test
	@DisplayName("Ein Stapel besitzt Karteikarten")
	void test_14() {
		Stapel stapel = StapelMother.initSet();

		assertThat(stapel.karteikartenAvailable()).isTrue();
	}

	@Test
	@DisplayName("Ein Stapel besitzt keine Karteikarten")
	void test_15() {
		Stapel stapel = StapelMother.initSetWithoutKarteikarten();

		assertThat(stapel.karteikartenAvailable()).isFalse();
	}

	@Test
	@DisplayName("Anzahl fälliger Karteikarten wird korrekt im Stapel berechnet")
	void test_16() {
		Stapel stapel = StapelMother.initSetWithALotKarteikarten();
		LocalDateTime today = LocalDateTime.of(2025, 1, 19, 10, 0, 0);

		assertThat(stapel.anzahlFälligeKarteikarten(today)).isEqualTo(4);
	}

	@Test
	@DisplayName("Anzahl fälliger Karteikarten ist 0, wenn der Stapel keine Karteikarten enthält")
	void test_17() {
		Stapel stapel = StapelMother.initSetWithoutKarteikarten();
		LocalDateTime today = LocalDateTime.of(2025, 1, 19, 10, 0, 0);

		assertThat(stapel.anzahlFälligeKarteikarten(today)).isEqualTo(0);
	}

	@Test
	@DisplayName("Anzahl neuer Karteikarten ist 0, wenn der Stapel keine Karteikarten enthält")
	void test_18() {
		Stapel stapel = StapelMother.initSetWithoutKarteikarten();

		assertThat(stapel.anzahlNeueKarteikarten()).isEqualTo(0);
	}

	@Test
	@DisplayName("Anzahl Karteikarten eines Stapels wird korrekt zurückgegeben")
	void test_19() {
		Stapel stapel1 = StapelMother.initSetWithoutKarteikarten();
		Stapel stapel2 = StapelMother.initSetWithALotKarteikarten();

		assertThat(stapel1.getKarteikartenSize()).isEqualTo(0);
		assertThat(stapel2.getKarteikartenSize()).isEqualTo(10);
	}

	@Test
	@DisplayName("Stapel wird korrekt auf Existenz einer Modul-Fach-Id geprüft")
	void test_20() {
		Stapel stapel1 = StapelMother.initSet();
		Stapel stapel2 = StapelMother.initSetWithoutModulFachId();

		assertThat(stapel1.hasModulFachId()).isTrue();
		assertThat(stapel2.hasModulFachId()).isFalse();
	}

	@Test
	@DisplayName("Anzahl der Lernstufen eines Stapels wird korrekt zurückgegeben")
	void test_21() {
		Stapel stapel = StapelMother.initSet();

		assertThat(stapel.getLernIntervalleLength()).isEqualTo(2);
	}

	@Test
	@DisplayName("Eine Karteikarte wird erfolgreich im Stapel durch seine Fach-Id gefunden")
	void test_22() {
		Stapel stapel = StapelMother.initSetWithALotKarteikarten();
		String findThisKard = stapel.getKarteikarten().get(3).getFachId().toString();

		Karteikarte k = stapel.findKarteikarteByFachId(findThisKard);

		assertThat(k.getFachId().toString()).isEqualTo(findThisKard);
	}

	@Test
	@DisplayName("Alle fälligen Karteikarten eines Stapels werden gefunden")
	void test_23() {
		Stapel stapel = StapelMother.initSetWithALotKarteikarten();
		LocalDateTime now = LocalDateTime.of(2025, 1, 19, 10, 0, 0);

		List<Karteikarte> fälligeKarten = stapel.getFälligeKarteikarten(now);

		assertThat(fälligeKarten.size()).isEqualTo(4);
	}

	@Test
	@DisplayName("Eine Antwort aus einer Karteikarte des Stapels wird erfolgreich entfernt")
	void test_24() {
		Stapel stapel = StapelMother.stapelWithSingleChoiceKarteikarte();
		Karteikarte karte = stapel.getKarteikarten().get(0);
		UUID karteId = karte.getFachId();
		int antwortIndex = 1;

		stapel.removeAntwortFromKarte(karteId, antwortIndex);

		assertThat(karte.getAntworten().size()).isEqualTo(2);
		assertThat(karte.getAntworten().get(1)).isNotEqualTo("aw3");
	}
}