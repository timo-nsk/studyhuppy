package com.studyhub.kartei.adapter.web.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})  // Gilt nur für Felder
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AntwortValidator.class)  // Verweist auf die Validator-Klasse
public @interface NoBlankAntworten {
	String message() default "Bitte geben Sie Antworten ein";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}

