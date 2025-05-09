package com.studyhub.kartei.adapter.web.validation;

import com.studyhub.kartei.domain.model.Antwort;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class AntwortValidator implements ConstraintValidator<NoBlankAntworten, List<Antwort>> {

	@Override
	public boolean isValid(List<Antwort> antworten, ConstraintValidatorContext context) {
		if (antworten == null || antworten.isEmpty()) {
			return false; // Leere Listen sind ungültig
		}

		for (Antwort antwort : antworten) {
			if (antwort == null || antwort.getAntwort() == null || antwort.getAntwort().trim().isEmpty()) {
				return false; // Eine ungültige Antwort gefunden
			}
		}
		return true; // Alle Antworten sind gültig
	}
}
