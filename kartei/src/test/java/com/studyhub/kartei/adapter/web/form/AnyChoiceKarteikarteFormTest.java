package com.studyhub.kartei.adapter.web.form;

import com.studyhub.kartei.domain.model.Antwort;
import com.studyhub.kartei.domain.model.FrageTyp;
import com.studyhub.kartei.domain.model.Karteikarte;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AnyChoiceKarteikarteFormTest {

	@Test
	@DisplayName("Wenn es sich um eine Sigle Choice-Karte handel, werden aus den Daten der AnyChoiceKarteikarteForm eine Karteikarte erstellt" +
			"und der Wahrheitswert der betroffenen Antwort, welche Wahr ist, richtig gesetzt")
	void test_1() {
		List<Antwort> antworten = List.of(new Antwort("aw1", false), new Antwort("aw2", false));
		AnyChoiceKarteikarteForm form = new AnyChoiceKarteikarteForm("f", antworten, 0, 2, "n", FrageTyp.SINGLE_CHOICE);

		Karteikarte karteikarte = form.toKarteikarte();

		assertThat(karteikarte.getFrage()).isEqualTo("f");
		assertThat(karteikarte.getAntworten().get(0).isWahrheit()).isTrue();
		assertThat(karteikarte.getAntworten().size()).isEqualTo(2);
		assertThat(karteikarte.getNotiz()).isEqualTo("n");
		assertThat(karteikarte.getFrageTyp()).isEqualTo(FrageTyp.SINGLE_CHOICE);
	}

	@Test
	@DisplayName("Wenn es sich um eine Multiple Choice Karte handelt, werden die Wahrheitswerte korrekt in die Antworten geladen")
	void test_2() {
		List<Antwort> antworten = List.of(
				new Antwort("aw1", false),
				new Antwort("aw2", true),
				new Antwort("aw3", false),
				new Antwort("aw4", true));
		AnyChoiceKarteikarteForm form = new AnyChoiceKarteikarteForm("f", antworten, 0, 4, "n", FrageTyp.MULTIPLE_CHOICE);

		Karteikarte karteikarte = form.toKarteikarte();

		assertThat(karteikarte.getFrage()).isEqualTo("f");
		assertThat(karteikarte.getAntworten().get(0).isWahrheit()).isFalse();
		assertThat(karteikarte.getAntworten().get(1).isWahrheit()).isTrue();
		assertThat(karteikarte.getAntworten().get(2).isWahrheit()).isFalse();
		assertThat(karteikarte.getAntworten().get(3).isWahrheit()).isTrue();
		assertThat(karteikarte.getAntworten().size()).isEqualTo(4);
		assertThat(karteikarte.getNotiz()).isEqualTo("n");
		assertThat(karteikarte.getFrageTyp()).isEqualTo(FrageTyp.MULTIPLE_CHOICE);
	}
}
