package com.studyhub.kartei.architecture;

import com.studyhub.kartei.util.ImplementTest;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.GeneralCodingRules;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import static com.studyhub.kartei.architecture.HaveCorrespondingTestClassesTest.*;
import static com.studyhub.kartei.architecture.NotUseFieldInjectionTest.*;
import static com.studyhub.kartei.architecture.UseLoggingTest.*;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "com.studyhub.kartei")
public class ArchitectureTest {

	JavaClasses onlyTestClasses = new ClassFileImporter()
			.withImportOption(ImportOption.Predefined.ONLY_INCLUDE_TESTS)
			.importPackages("com.studyhub.kartei");

	// Wird fehlschlagen
	//@ArchTest ArchRule allClassesShouldHaveCorrespondingTestClasses = classes().should(HAVE_CORRESPONDING_TEST_CLASSES);

	@ArchTest ArchRule allProductiveClassesShouldNotUseFieldInjection = classes().should(NOT_USE_FIELD_INJECTION);
	@ArchTest ArchRule classesThatAreAnnotatedWithServiceUseLogging = classes().that().areAnnotatedWith(Service.class).should(USE_LOGGING);
	@ArchTest ArchRule rule_1 = classes().that().haveSimpleNameEndingWith("Service").should().beAnnotatedWith(Service.class);
	@ArchTest ArchRule rule_2 = classes().that().areAnnotatedWith(Service.class).should().haveSimpleNameEndingWith("Service");
	@ArchTest ArchRule rule_3 = classes().that().haveSimpleNameEndingWith("Impl").should().beAnnotatedWith(Repository.class);
	@ArchTest ArchRule rule_4 = classes().that().areAnnotatedWith(Repository.class).should().haveSimpleNameEndingWith("Impl");
	@ArchTest ArchRule rule_5 = classes().that().areAnnotatedWith(Controller.class).or().areAnnotatedWith(RestController.class).should().haveSimpleNameEndingWith("Controller");
	@ArchTest ArchRule rule_6 = classes().that().haveSimpleNameEndingWith("Controller").should().beAnnotatedWith(Controller.class).orShould().beAnnotatedWith(RestController.class);
	@ArchTest ArchRule rule_7 = GeneralCodingRules.testClassesShouldResideInTheSamePackageAsImplementation();
	@ArchTest ArchRule rule_8 = GeneralCodingRules.NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS;
	@ArchTest ArchRule rule_9 = classes().that().areAnnotatedWith(Aspect.class).should().haveSimpleNameEndingWith("Aspect");
	@ArchTest ArchRule rule_10 = classes().that().haveSimpleNameEndingWith("Aspect").should().beAnnotatedWith(Aspect.class);

	@Test
	void rule_11() {
		ArchRule rule_11 = noClasses().should().beAnnotatedWith(ImplementTest.class);
		rule_11.check(onlyTestClasses);
	}
}
