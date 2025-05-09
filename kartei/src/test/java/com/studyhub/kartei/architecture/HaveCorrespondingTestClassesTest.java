package com.studyhub.kartei.architecture;

import com.studyhub.kartei.KarteiApplication;
import com.studyhub.kartei.KarteiApplicationTests;
import com.studyhub.kartei.TestKarteiApplication;
import com.studyhub.kartei.adapter.config.CacheConfig;
import com.studyhub.kartei.util.JsoupUtil;
import com.studyhub.kartei.util.KarteikarteGelerntEventMother;
import com.studyhub.kartei.util.KarteikarteMother;
import com.studyhub.kartei.util.StapelMother;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import org.testcontainers.utility.TestcontainersConfiguration;

public class HaveCorrespondingTestClassesTest extends ArchCondition<JavaClass> {

	public HaveCorrespondingTestClassesTest(String description) {
		super(description);
	}

	public static final HaveCorrespondingTestClassesTest HAVE_CORRESPONDING_TEST_CLASSES = new HaveCorrespondingTestClassesTest("have corresponding test classes");

	@Override
	public void check(JavaClass javaClass, ConditionEvents events) {
		//System.out.println("----checking-----");

		String className = javaClass.getSimpleName();

		//Vorerst diese Klassen skippen, weil es für die Repository-Interface Testklassen gibt
		if (className.endsWith("Dao") || className.endsWith("Impl")) {
			//System.out.println("Skipping DAO class: " + className);
			return;
		}

		if (ignoreClasses(javaClass)) return;

		//System.out.println("testClassName: " + className);

		String expectedTestClassName = className.endsWith("Test") ? className : className + "Test";
		//System.out.println("expectedTestClassName: " + expectedTestClassName);

		JavaClass testClass = javaClass.getPackage().getClasses().stream()
				.filter(c -> c.getSimpleName().equals(expectedTestClassName))
				.findFirst()
				.orElse(null);

		//System.out.println("the JavaClass: " + javaClass);

		if (testClass == null) {
			//System.out.println("Keine korrespondierende Testklasse für " + javaClass.getName());
			events.add(SimpleConditionEvent.violated(javaClass, "no test class for " + javaClass.getName()));
		}
	}

	public boolean ignoreClasses(JavaClass javaClass) {
		Class<?>[] classes = {
				KarteiApplication.class, KarteiApplicationTests.class, TestKarteiApplication.class,
				TestcontainersConfiguration.class, CacheConfig.class, JsoupUtil.class,
				KarteikarteGelerntEventMother.class, KarteikarteMother.class, StapelMother.class
		};

		for (Class<?> c : classes) {
			if (c.getName().equals(javaClass.getName())) {
				return true;
			}
		}

		return false;
	}
}
