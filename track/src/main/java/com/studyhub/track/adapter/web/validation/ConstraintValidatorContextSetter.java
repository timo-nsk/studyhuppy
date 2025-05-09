package com.studyhub.track.adapter.web.validation;

import jakarta.validation.ConstraintValidatorContext;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class ConstraintValidatorContextSetter {

	public void contextSetter(ConstraintValidatorContext constraintValidatorContext, String s, String errorMessage) {
		constraintValidatorContext.disableDefaultConstraintViolation();
		constraintValidatorContext.buildConstraintViolationWithTemplate(errorMessage)
				.addPropertyNode(s)
				.addConstraintViolation();
	}
}
