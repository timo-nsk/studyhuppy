package com.studyhub.kartei.adapter.web.controller;

import com.studyhub.kartei.adapter.web.controller.SetFragetypRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class SetFragetypRequestTest {

	@Test
	@DisplayName("Eine valider Request wird als true bewertet")
	void test_01() {
		SetFragetypRequest req = new SetFragetypRequest("NORMAL", UUID.randomUUID().toString());

		assertThat(req.isValidRequest()).isTrue();
	}

	@Test
	@DisplayName("Eine Request mit inkorrektem Fragetyp wird als false bewertet")
	void test_02() {
		SetFragetypRequest req = new SetFragetypRequest(null, UUID.randomUUID().toString());

		assertThat(req.isValidRequest()).isFalse();
	}

	@Test
	@DisplayName("Eine Request mit inkorrekt formattierter UUID wird als false bewertet")
	void test_03() {
		SetFragetypRequest req = new SetFragetypRequest("NORMAL", "uuid");

		assertThat(req.isValidRequest()).isFalse();
	}

	@Test
	@DisplayName("Eine Request mit invaliden Parametern wird als false bewertet")
	void test_04() {
		SetFragetypRequest req = new SetFragetypRequest(null, "uuid");

		assertThat(req.isValidRequest()).isFalse();
	}

	@Test
	@DisplayName("Eine Request ohne Werte ist inavlid")
	void test_05() {
		SetFragetypRequest req = new SetFragetypRequest(null, null);

		assertThat(req.isValidRequest()).isFalse();
	}
}
