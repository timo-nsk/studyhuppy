package com.studyhub.authentication.web;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UsernameValidator.class)
public @interface IsAvailable {
	String message() default "Benutzername ist schon vergeben";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
