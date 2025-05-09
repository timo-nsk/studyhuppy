package com.studyhub.kartei.model;

import com.studyhub.kartei.domain.model.Schwierigkeit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SchwierigkeitTest {

	@Test
	@DisplayName(" values() gibt alles Schwiergkeiten wieder")
	void test_1() {
		Schwierigkeit[] values = Schwierigkeit.values();
		assertThat(values).containsExactly(Schwierigkeit.EASY, Schwierigkeit.NORMAL, Schwierigkeit.HARD);
	}

	@Test
	@DisplayName("Der Richtige Enum-Wert von Schwierigkeit wird zurückgegeben")
	void test_2() {
		assertThat(Schwierigkeit.valueOf("EASY")).isEqualTo(Schwierigkeit.EASY);
		assertThat(Schwierigkeit.valueOf("NORMAL")).isEqualTo(Schwierigkeit.NORMAL);
		assertThat(Schwierigkeit.valueOf("HARD")).isEqualTo(Schwierigkeit.HARD);
	}

	@Test
	@DisplayName("Oridinal-Werte sind korrekt")
	void test_3() {
		assertThat(Schwierigkeit.EASY.ordinal()).isEqualTo(0);
		assertThat(Schwierigkeit.NORMAL.ordinal()).isEqualTo(1);
		assertThat(Schwierigkeit.HARD.ordinal()).isEqualTo(2);
	}
}
