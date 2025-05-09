package com.studyhub.kartei.adapter.web.form;

import com.studyhub.kartei.domain.model.Stapel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class StapelFormTest {

	@Test
	@DisplayName("Mapping korrekt, wenn ModulFachId vorhanden")
	void test_01() {
		StapelForm form = new StapelForm("Modul", "Cooles Set", "3f89c3e8-9f1b-4d5c-98e3-2c5a8c51776b", "10m,10h,10d");

		Stapel set = form.toStapel("username");

		assertThat(set.getName()).isEqualTo("Modul");
		assertThat(set.getBeschreibung()).isEqualTo("Cooles Set");
		assertThat(set.getModulFachId()).isEqualTo(UUID.fromString("3f89c3e8-9f1b-4d5c-98e3-2c5a8c51776b"));
		assertThat(set.getUsername()).isEqualTo("username");
	}

	@Test
	@DisplayName("Mapping korrekt, wenn ModulFachId nicht vorhanden")
	void test_02() {
		StapelForm form = new StapelForm("Modul", "Cooles Set", "", "10m,10h,10d");

		Stapel set = form.toStapel("username");

		assertThat(set.getName()).isEqualTo("Modul");
		assertThat(set.getBeschreibung()).isEqualTo("Cooles Set");
		assertThat(set.getModulFachId()).isEqualTo(null);
		assertThat(set.getUsername()).isEqualTo("username");
	}
}
