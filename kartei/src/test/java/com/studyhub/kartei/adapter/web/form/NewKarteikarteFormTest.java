package com.studyhub.kartei.adapter.web.form;

import com.studyhub.kartei.domain.model.FrageTyp;
import com.studyhub.kartei.domain.model.Karteikarte;
import com.studyhub.kartei.util.KarteikarteMother;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NewKarteikarteFormTest {

	@Test
	@DisplayName("Aus den Daten der NewKarteikarteForm wird eine Karteikarte erstellt")
	void test_1() {
		NewKarteikarteForm form = new NewKarteikarteForm("f", "a", "n", "NORMAL");

		Karteikarte karteikarte = form.toKarteikarte();

		assertThat(karteikarte.getFrage()).isEqualTo("f");
		assertThat(karteikarte.getAntwort()).isEqualTo("a");
		assertThat(karteikarte.getNotiz()).isEqualTo("n");
		assertThat(karteikarte.getFrageTyp()).isEqualTo(FrageTyp.NORMAL);
	}

	@Test
	@DisplayName("Aus den Daten einer Karteikarte wird eine NewKarteikarteForm erstellt")
	void test_2() {
		Karteikarte karte = KarteikarteMother.newKarteikarte("f", "a");

		NewKarteikarteForm form = NewKarteikarteForm.toForm(karte);

		assertThat(form.frage()).isEqualTo("f");
		assertThat(form.antwort()).isEqualTo("a");
		assertThat(form.notiz()).isEqualTo("Notiz");
		assertThat(form.frageTyp()).isEqualTo("NORMAL");
	}
}
