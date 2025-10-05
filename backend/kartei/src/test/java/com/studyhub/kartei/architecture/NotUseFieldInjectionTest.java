package com.studyhub.kartei.architecture;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import org.springframework.beans.factory.annotation.Autowired;

public class NotUseFieldInjectionTest extends ArchCondition<JavaClass> {
	public NotUseFieldInjectionTest(String description, Object... args) { super(description, args); }

	public static final NotUseFieldInjectionTest NOT_USE_FIELD_INJECTION = new NotUseFieldInjectionTest("not use field injection");

	@Override
	public void check(JavaClass javaClass, ConditionEvents events) {
		String className = javaClass.getSimpleName();

		if (className.endsWith("Test")) return;

		boolean hasFieldWithAnnotation = javaClass.getFields().stream()
				.anyMatch(field -> field.isAnnotatedWith(Autowired.class));
		if (hasFieldWithAnnotation) {
			events.add(SimpleConditionEvent.violated(javaClass, "Field injection found in class " + javaClass.getName()));
		}
	}
}
