package com.studyhub.kartei.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class OnionArchitectureTest {

	JavaClasses classes = new ClassFileImporter()
			.withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
			.importPackages("com.example.kartei");

	@Test
	@DisplayName("Die Onion-Architektur wird eingehalten")
	void test_1() {
		ArchRule onionArchitecture = Architectures.onionArchitecture()
				.domainModels("..domain.model..")
				.domainServices("..domain.model..")
				.applicationServices("..service.application..")
				.adapter("adapter", "..adapter..");

		onionArchitecture.check(classes);
	}
}
