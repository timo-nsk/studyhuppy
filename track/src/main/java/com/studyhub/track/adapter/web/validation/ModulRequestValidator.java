package com.studyhub.track.adapter.web.validation;

import com.studyhub.track.adapter.web.ModulRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;

public class ModulRequestValidator implements ConstraintValidator<ValidModulRequest, List<ModulRequest>> {

	private final ConstraintValidatorContextSetter cs;

	public ModulRequestValidator(ConstraintValidatorContextSetter cs) {
		this.cs = cs;
	}

	@Override
	public boolean isValid(List<ModulRequest> modulRequests, ConstraintValidatorContext constraintValidatorContext) {
		if (modulRequests == null || modulRequests.isEmpty()) return false;

		for (ModulRequest modulRequest : modulRequests) {
			if (modulRequest == null) continue;
			if (modulRequest.modulName() == null || modulRequest.modulName().isEmpty()) {
				cs.contextSetter(constraintValidatorContext, "module", "Bitte geben Sie einen Modulnamen ein");
				return false;
			} else if(modulRequest.creditPoints() == null) {
				cs.contextSetter(constraintValidatorContext, "module", "Bitte geben Sie einen Wert für Kreditpunkte an");
				return false;
			}
		}

		return false;
	}
}