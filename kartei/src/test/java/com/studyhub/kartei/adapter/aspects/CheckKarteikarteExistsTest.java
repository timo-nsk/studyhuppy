package com.studyhub.kartei.adapter.aspects;

import org.junit.jupiter.api.Test;
import java.lang.annotation.*;
import java.lang.reflect.Method;
import static org.junit.jupiter.api.Assertions.*;

class CheckKarteikarteExistsTest {

	@CheckKarteikarteExists
	public void dummyMethod() {
	}

	@Test
	void testAnnotationIsPresent() throws NoSuchMethodException {
		Method method = this.getClass().getMethod("dummyMethod");
		Annotation annotation = method.getAnnotation(CheckKarteikarteExists.class);

		assertNotNull(annotation, "Annotation CheckKarteikarteExists sollte vorhanden sein");
	}

	@Test
	void testAnnotationRetentionPolicy() {
		Retention retention = CheckKarteikarteExists.class.getAnnotation(Retention.class);
		assertNotNull(retention, "Annotation sollte @Retention enthalten");
		assertEquals(RetentionPolicy.RUNTIME, retention.value(), "RetentionPolicy sollte RUNTIME sein");
	}

	@Test
	void testAnnotationTarget() {
		Target target = CheckKarteikarteExists.class.getAnnotation(Target.class);
		assertNotNull(target, "Annotation sollte @Target enthalten");
		assertArrayEquals(new ElementType[]{ElementType.METHOD}, target.value(), "Target sollte METHOD sein");
	}
}
