package com.studyhub.track.adapter.web.validation;

import com.studyhub.track.adapter.web.SemesterForm;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class DaterOrderValidator implements ConstraintValidator<ValidDateOder, SemesterForm> {

	private final ConstraintValidatorContextSetter cs;

	public DaterOrderValidator(ConstraintValidatorContextSetter constraintValidatorContextSetter) {
		this.cs = constraintValidatorContextSetter;
	}

	@Override
	public boolean isValid(SemesterForm semesterForm, ConstraintValidatorContext constraintValidatorContext) {

		LocalDate semesterBeginn = semesterForm.getSemesterBeginn();
		LocalDate semesterEnde = semesterForm.getSemesterEnde();

		LocalDate vlBeginn = semesterForm.getVlBeginn();
		LocalDate vlEnde = semesterForm.getVlEnde();

		if(semesterBeginn == null || semesterEnde == null || vlBeginn == null || vlEnde == null) return true;

		if(semesterBeginn.isAfter(vlBeginn)) {
			cs.contextSetter(constraintValidatorContext, "semesterBeginn", "Semester muss vor den Vorlesungen starten");
			return false;
		} else if(vlBeginn.isAfter(semesterEnde)) {
			cs.contextSetter(constraintValidatorContext, "vlBeginn", "Vorlesungen könne nicht nach dem Semester beginnen");
			return false;
		} else if(semesterEnde.isBefore(vlEnde)) {
			cs.contextSetter(constraintValidatorContext, "semesterEnde", "Semester kann nicht vor der Vorlesungszeit enden");
			return false;
		} else if(semesterBeginn.isEqual(semesterEnde)) {
			cs.contextSetter(constraintValidatorContext, "semesterEnde", "Semesterbeginn und -ende können nicht gleich sein");
			return false;
		} else if(vlBeginn.isEqual(vlEnde)) {
			cs.contextSetter(constraintValidatorContext, "semesterEnde", "Vorlesungbeginn und -ende können nicht gleich sein");
			return false;
		}   else if (vlEnde.isBefore(semesterBeginn)) {
			cs.contextSetter(constraintValidatorContext, "semesterEnde", "Vorlesungen können nicht vor dem Semesterbeginn enden");
			return false;
		}

		return true;
	}
}
