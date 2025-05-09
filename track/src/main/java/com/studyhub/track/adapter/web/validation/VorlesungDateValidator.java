package com.studyhub.track.adapter.web.validation;

import com.studyhub.track.adapter.web.SemesterForm;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class VorlesungDateValidator implements ConstraintValidator<ValidVorlesungDates, SemesterForm> {

	private final ConstraintValidatorContextSetter cs;

	public VorlesungDateValidator(ConstraintValidatorContextSetter cs) {
		this.cs = cs;
	}

	@Override
	public boolean isValid(SemesterForm semesterForm, ConstraintValidatorContext constraintValidatorContext) {
		LocalDate beginn = semesterForm.getVlBeginn();
		LocalDate ende = semesterForm.getVlEnde();

		if (beginn == null) {
			cs.contextSetter(constraintValidatorContext, "vlBeginn", "Bitte geben Sie ein Vorlesungsbeginn-Datum");
			return false;
		} else if(ende == null) {
			cs.contextSetter(constraintValidatorContext, "vlEnde", "Bitte geben Sie ein Vorlesungsende-Datum");
			return false;
		}

		if (beginn.isBefore(ende)) {
			return true;
		} else {
			cs.contextSetter(constraintValidatorContext, "vlEnde", "Das Vorlesungsende muss nach dem Vorlesungsbeginn liegen");
			return false;
		}

	}
}
