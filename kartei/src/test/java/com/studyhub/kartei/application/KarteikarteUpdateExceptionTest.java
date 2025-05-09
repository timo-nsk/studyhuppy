package com.studyhub.kartei.application;


import com.studyhub.kartei.service.application.KarteikarteUpdateException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class KarteikarteUpdateExceptionTest {

	@Test
	@DisplayName("Korrekte Instanziierung")
	void shouldCreateExceptionWithMessage() {
		String message = "Fehler beim Aktualisieren der Karteikarte";
		KarteikarteUpdateException exception = new KarteikarteUpdateException(message);

		assertThat(exception).isInstanceOf(RuntimeException.class);
		assertThat(exception).hasMessage(message);
	}

	@Test
	@DisplayName("Prüfe ob Exception mit Message geworfen wird")
	void test_2() {
		String message = "Update fehlgeschlagen";

		Throwable thrown = catchThrowable(() -> {
			throw new KarteikarteUpdateException(message);
		});

		assertThat(thrown).isInstanceOf(KarteikarteUpdateException.class)
				.hasMessage(message);
	}
}