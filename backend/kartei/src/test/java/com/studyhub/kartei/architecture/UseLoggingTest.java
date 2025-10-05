package com.studyhub.kartei.architecture;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import org.slf4j.Logger;

public class UseLoggingTest extends ArchCondition<JavaClass> {
	public UseLoggingTest(String description, Object... args) {
		super(description, args);
	}

	public static final UseLoggingTest USE_LOGGING = new UseLoggingTest("use logging");

	@Override
	public void check(JavaClass item, ConditionEvents events) {
		String className = item.getSimpleName();

		boolean usesLogger = item.getAllFields().stream()
				.anyMatch(field -> field.getRawType().isAssignableTo(Logger.class));

		if (!usesLogger) {
			events.add(SimpleConditionEvent.violated(item, "Class " + className + " does noz contain a Logger field."));
		}
	}
}
