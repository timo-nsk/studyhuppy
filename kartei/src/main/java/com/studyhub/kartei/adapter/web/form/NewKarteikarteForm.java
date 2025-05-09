package com.studyhub.kartei.adapter.web.form;

import com.studyhub.kartei.domain.model.FrageTyp;
import com.studyhub.kartei.domain.model.Karteikarte;
import jakarta.validation.constraints.NotBlank;

public record NewKarteikarteForm(
		@NotBlank(message = "Bitte geben Sie eine Frage ein")
		String frage,

		@NotBlank(message = "Bitte geben Sie eine Antwort ein")
		String antwort,

        String notiz,
		String frageTyp) {

	public Karteikarte toKarteikarte() {
		FrageTyp ftype = switch (frageTyp()) {
			case "SINGLE_CHOICE" -> FrageTyp.SINGLE_CHOICE;
			case "MULTIPLE_CHOICE" -> FrageTyp.MULTIPLE_CHOICE;
			default -> FrageTyp.NORMAL;
		};

		return Karteikarte.initNewKarteikarte(frage(), antwort(), notiz(), ftype);
	}

	public static NewKarteikarteForm toForm(Karteikarte karte) {
		return new NewKarteikarteForm(karte.getFrage(), karte.getAntwort(), karte.getNotiz(), karte.getFrageTyp().toString());
	}
}
