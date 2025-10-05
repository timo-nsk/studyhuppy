package com.studyhub.kartei.adapter.aspects;

import org.junit.jupiter.api.Test;

import java.lang.annotation.*;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

public class CheckStapelExistsTest {

	@CheckStapelExists
	public void dummyMethod() {
	}

	@Test
	void testAnnotationIsPresent() throws NoSuchMethodException {
		Method method = this.getClass().getMethod("dummyMethod");
		Annotation annotation = method.getAnnotation(CheckStapelExists.class);

		assertNotNull(annotation, "Annotation CheckStapelExists sollte vorhanden sein");
	}

	@Test
	void testAnnotationRetentionPolicy() {
		Retention retention = CheckStapelExists.class.getAnnotation(Retention.class);
		assertNotNull(retention, "Annotation sollte @Retention enthalten");
		assertEquals(RetentionPolicy.RUNTIME, retention.value(), "RetentionPolicy sollte RUNTIME sein");
	}

	@Test
	void testAnnotationTarget() {
		Target target = CheckStapelExists.class.getAnnotation(Target.class);
		assertNotNull(target, "Annotation sollte @Target enthalten");
		assertArrayEquals(new ElementType[]{ElementType.METHOD}, target.value(), "Target sollte METHOD sein");
	}
}
