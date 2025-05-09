package com.studyhub.track.adapter.web.validation;

import com.studyhub.track.adapter.web.SemesterForm;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class SemesterDateValidator implements ConstraintValidator<ValidSemesterDates, SemesterForm> {

	private final ConstraintValidatorContextSetter cs;

	public SemesterDateValidator(ConstraintValidatorContextSetter cs) {
		this.cs = cs;
	}

	@Override
	public boolean isValid(SemesterForm semesterForm, ConstraintValidatorContext constraintValidatorContext) {
		LocalDate beginn = semesterForm.getSemesterBeginn();
		LocalDate ende = semesterForm.getSemesterEnde();

		if (beginn == null) {
			cs.contextSetter(constraintValidatorContext, "semesterBeginn", "Bitte Geben Sie ein Datum für den Semesterbeginn ein");
			return false;
		} else if(ende == null ) {
			cs.contextSetter(constraintValidatorContext, "semesterEnde", "Bitte Geben Sie ein Datum für das Semesterende ein");
			return false;
		}

		if (beginn.isBefore(ende)) {
			return true;
		} else {
			cs.contextSetter(constraintValidatorContext, "semesterEnde", "Das Semester-Ende muss nach dem Semester-Beginn liegen.");
			return false;
		}
	}
}
