package com.studyhub.kartei.model;

import com.studyhub.kartei.domain.model.AggregateInconsistencyException;
import com.studyhub.kartei.domain.model.FrageTyp;
import com.studyhub.kartei.domain.model.Karteikarte;
import com.studyhub.kartei.domain.model.Schwierigkeit;
import com.studyhub.kartei.util.KarteikarteMother;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

public class KarteikarteTest {

	@Test
	@DisplayName("Factory-Methode initiiert Karteikarte mit Default-Werten korrekt")
	void test_1() {
		Karteikarte k = Karteikarte.initNewKarteikarte("frage", "antwort", "notiz", FrageTyp.NORMAL);

		assertThat(k.getFachId()).isNotNull();
		assertThat(k.getFrage()).isEqualTo("frage");
		assertThat(k.getAntwort()).isEqualTo("antwort");
		assertThat(k.getErstelltAm()).isNotNull();
		assertThat(k.getLetzteAenderungAm()).isEqualTo(k.getErstelltAm());
		assertThat(k.getFaelligAm()).isEqualTo(k.getErstelltAm());
		assertThat(k.getNotiz()).isEqualTo("notiz");
		assertThat(k.getWasHard()).isEqualTo(0);
		assertThat(k.getFrageTyp()).isEqualTo(FrageTyp.NORMAL);
		assertThat(k.getAntwortzeitSekunden()).isEqualTo(0);
		assertThat(k.getLernstufen()).isNull();
	}

	@Test
	@DisplayName("Lernstufen(3 Stück) einer Karte werden erfolgreich geupdated, basierend auf Schwierigkeit.NORMAL")
	void test_2() {
		Karteikarte normalKarte = KarteikarteMother.newKarteikarteWithLernstufen("10m,2h,2d");

		boolean erfolg = normalKarte.updateLernstufen(Schwierigkeit.NORMAL);

		assertThat(erfolg).isTrue();
		assertThat(normalKarte.getLernstufen()).isEqualTo("2h,2d,4d");
	}

	@Test
	@DisplayName("Lernstufen(3 Stück) einer Karte werden erfolgreich geupdated, basierend auf Schwierigkeit.EASY")
	void test_3() {
		Karteikarte normalKarte = KarteikarteMother.newKarteikarteWithLernstufen("10m,2h,2d");

		boolean erfolg = normalKarte.updateLernstufen(Schwierigkeit.EASY);

		assertThat(erfolg).isTrue();
		assertThat(normalKarte.getLernstufen()).isEqualTo("2d,4d,8d");
	}

	@Test
	@DisplayName("Lernstufen(3 Stück) einer Karte werden nicht geupdated, wenn die Schwiergkeit HARD war")
	void test_4() {
		Karteikarte normalKarte = KarteikarteMother.newKarteikarteWithLernstufen("10m,2h,2d");

		boolean erfolg = normalKarte.updateLernstufen(Schwierigkeit.HARD);

		assertThat(erfolg).isTrue();
		assertThat(normalKarte.getLernstufen()).isEqualTo("10m,2h,2d");
	}

	@Test
	@DisplayName("Das wasHard-Feld wird erfolgreich inkrementiert")
	void test_5() {
		Karteikarte normalKarte = KarteikarteMother.newKarteikarteWithLernstufen("10m,2h,2d");

		normalKarte.karteikarteWasHard();

		assertThat(normalKarte.getWasHard()).isEqualTo(1);
	}

	@Test
	@DisplayName("Lernstufen(4 Stück) einer Karte werden erfolgreich geupdated, basierend auf Schwierigkeit.NORMAL")
	void test_6() {
		Karteikarte normalKarte = KarteikarteMother.newKarteikarteWithLernstufen("10m,2h,2d,4d");

		boolean erfolg = normalKarte.updateLernstufen(Schwierigkeit.NORMAL);

		assertThat(erfolg).isTrue();
		assertThat(normalKarte.getLernstufen()).isEqualTo("2h,2d,4d");
	}

	@Test
	@DisplayName("Lernstufen(4 Stück) einer Karte werden erfolgreich geupdated, basierend auf Schwierigkeit.EASY")
	void test_7() {
		Karteikarte normalKarte = KarteikarteMother.newKarteikarteWithLernstufen("10m,2h,2d,4d");

		boolean erfolg = normalKarte.updateLernstufen(Schwierigkeit.EASY);

		assertThat(erfolg).isTrue();
		assertThat(normalKarte.getLernstufen()).isEqualTo("2d,4d,8d,16d");
	}

	@Test
	@DisplayName("Lernstufen einer Karteikarte können nicht im falschen Format gesetzt werden")
	void test_8() {
		String invalidLernstufen = "2m2h2d";
		Karteikarte normalKarte = KarteikarteMother.newKarteikarteWithLernstufen("10m,2h,2d,4d");

		assertThrows(AggregateInconsistencyException.class, () -> {
			normalKarte.setLernstufen(invalidLernstufen);
		});
	}

	@Test
	@DisplayName("Lernstufen einer Karteikarte können nicht im falschen Format gesetzt werden")
	void test_9() {
		String invalidLernstufen = "2m2h2d";
		String invalidLernstufen2 = "222z";
		String invalidLernstufen3 = "2m;2h!2d";
		String invalidLernstufen4 = "";
		String invalidLernstufen5 = null;
		Karteikarte normalKarte = KarteikarteMother.newKarteikarteWithLernstufen("10m,2h,2d,4d");

		assertThrows(AggregateInconsistencyException.class, () -> {
			normalKarte.setLernstufen(invalidLernstufen);
		});

		assertThrows(AggregateInconsistencyException.class, () -> {
			normalKarte.setLernstufen(invalidLernstufen2);
		});

		assertThrows(AggregateInconsistencyException.class, () -> {
			normalKarte.setLernstufen(invalidLernstufen3);
		});

		assertThrows(AggregateInconsistencyException.class, () -> {
			normalKarte.setLernstufen(invalidLernstufen4);
		});

		assertThrows(AggregateInconsistencyException.class, () -> {
			normalKarte.setLernstufen(invalidLernstufen5);
		});
	}

	@Test
	@DisplayName("Lernstufen einer Karteikarte können gesetzt werden, wenn sie das richtige Format haben")
	void test_10() {
		String validLernstufen = "2m,2h,2d";
		Karteikarte normalKarte = KarteikarteMother.newKarteikarteWithLernstufen("10m,2h,2d,4d");

		assertDoesNotThrow(() -> {
			normalKarte.setLernstufen(validLernstufen);
		});
		assertThat(normalKarte.getLernstufen()).isEqualTo(validLernstufen);
	}

	@Test
	@DisplayName("Eine Karteikarte wird als neu erkannt")
	void test_11() {
		Karteikarte actualNewKarteikarte = KarteikarteMother.newKarteikarte("f", "a");

		boolean res = actualNewKarteikarte.isNewKarteikarte();

		assertTrue(res);
	}

	@Test
	@DisplayName("Eine Karteikarte wird nicht als neu erkannt")
	void test_12() {
		Karteikarte actualNewKarteikarte = KarteikarteMother.newKarteikarte("f", "a");
		actualNewKarteikarte.setFaelligAm(LocalDateTime.MAX);

		boolean res = actualNewKarteikarte.isNewKarteikarte();

		assertFalse(res);
	}
}
