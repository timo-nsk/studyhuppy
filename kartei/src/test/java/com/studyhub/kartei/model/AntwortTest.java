package com.studyhub.kartei.model;

import com.studyhub.kartei.domain.model.Antwort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AntwortTest {
	@Test
	@DisplayName("NotArgs und AllArgsKonstruktor erstellt erfolgreich eine Instanz")
	void test_1() {
		Antwort antwort1 = new Antwort();
		Antwort antwort2 = new Antwort("Ja", true);

		assertThat(antwort1).isNotNull();
		assertThat(antwort2.getAntwort()).isEqualTo("Ja");
		assertThat(antwort2.isWahrheit()).isTrue();
	}

	@Test
	@DisplayName("Getter/Setter funktionieren")
	void shouldSetAndGetAntwort() {
		Antwort antwort = new Antwort("Ja", true);
		Antwort antwort2 = new Antwort("Ja", true);
		antwort2.setWahrheit(false);
		assertThat(antwort.getAntwort()).isEqualTo("Ja");
		assertThat(antwort.isWahrheit()).isTrue();
		assertThat(antwort2.isWahrheit()).isFalse();
	}

	@Test
	@DisplayName("Hash/Equals funktionieren")
	void shouldTestEqualsAndHashCode() {
		Antwort a1 = new Antwort("Ja", true);
		Antwort a2 = new Antwort("Ja", true);
		Antwort a3 = new Antwort("Ja", false);

		assertThat(a1).isEqualTo(a2);
		assertThat(a1.hashCode()).isEqualTo(a2.hashCode());
		assertThat(a1).isNotEqualTo(a3);
	}
}
