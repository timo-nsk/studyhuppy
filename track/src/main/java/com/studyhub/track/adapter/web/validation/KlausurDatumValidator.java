package com.studyhub.track.adapter.web.validation;

import com.studyhub.track.adapter.web.ModulForm;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class KlausurDatumValidator implements ConstraintValidator<ValidKlausurDatum, ModulForm> {
	@Override
	public boolean isValid(ModulForm modulForm, ConstraintValidatorContext constraintValidatorContext) {
		LocalDate klausurDatum = modulForm.klausurDatum();
		String time = modulForm.time();

		//Ein Klausur-Datum muss nicht unbedingt eingegeben werden, aber ein Datum ist nur dann valid, wenn auch eine Zeitangabe eingegeben wurde

		if ((klausurDatum != null && (time == null || time.isBlank())) ||
				(time != null && !time.isBlank() && klausurDatum == null)) {
			return false; // Ungültig: Entweder Datum ohne Zeit oder Zeit ohne Datum
		}

		return true;
	}
}
