package com.studyhub.kartei.adapter.web.form;

import com.studyhub.kartei.domain.model.Stapel;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;



public record StapelForm(
		@NotEmpty(message = "Geben sie einen Setnamen ein")
		String setName,

		String beschreibung,

		String modulFachId,

		@Pattern(
				regexp = "^(?:(?:[1-5]?[0-9]m|(?:1[0-9]|2[0-3]|[1-9])h|(?:[1-2][0-9]|3[0-1]|[1-9])d)(?:,(?:[1-5]?[0-9]m|(?:1[0-9]|2[0-3]|[1-9])h|(?:[1-2][0-9]|3[0-1]|[1-9])d))*)$",
				message = "Ungültiges Zeitformat! Erlaubt: 5m,15m,30m,2h,1d"
		)
		String lernIntervalle

) {
	public Stapel toStapel(String username) {
		if(modulFachId == null || modulFachId.equals("")) {
			return new Stapel(
					null,
					setName,
					beschreibung,
					username,
					lernIntervalle);
		} else {
			return new Stapel(
					UUID.fromString(modulFachId),
					setName,
					beschreibung,
					username,
					lernIntervalle);
		}
	}
}
