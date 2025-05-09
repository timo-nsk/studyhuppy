package com.studyhub.track.service;

import com.studyhub.track.application.service.TimeConverter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalTime;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TimeConverterTest {

	private static TimeConverter tc;

	@BeforeAll
	public static void init() {
		tc = new TimeConverter();
	}

	@Test
	@DisplayName("10:30 werden korrekt in Sekunden konvertiert")
	void test_1() {
		String time = "01:30";

		int converted = tc.timeToSeconds(time);

		assertThat(converted).isEqualTo(5400);
	}

	@Test
	@DisplayName("25:60 wird als ungültige Eingabe erkannt.")
	void test_2() {
		String time = "25:60";

		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			int converted = tc.timeToSeconds(time);
		});

		assertEquals("hh and mm out of range", thrown.getMessage());
	}

	@Test
	@DisplayName("23:-50 wird als ungültige Eingabe erkannt.")
	void test_3() {
		String time = "23:-50";

		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			int converted = tc.timeToSeconds(time);
		});

		assertEquals("hh and mm out of range", thrown.getMessage());
	}

	@Test
	@DisplayName("-23:49 wird als ungültige Eingabe erkannt.")
	void test_4() {
		String time = "-23:49";

		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			int converted = tc.timeToSeconds(time);
		});

		assertEquals("hh and mm out of range", thrown.getMessage());
	}

	@Test
	@DisplayName("-23:-49 wird als ungültige Eingabe erkannt.")
	void test_5() {
		String time = "-23:-49";

		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			int converted = tc.timeToSeconds(time);
		});

		assertEquals("hh and mm out of range", thrown.getMessage());
	}

	@Test
	@DisplayName("-23-49 wird als ungültige Eingabe erkannt.")
	void test_6() {
		String time = "-23-49";

		IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
			int converted = tc.timeToSeconds(time);
		});

		assertEquals("hh and mm out of range", thrown.getMessage());
	}

	@Test
	@DisplayName("Der Zeit-String '11:11' wird korrekt in ein LocalDate umgewandelt")
	void test_7() {
		String time = "11:11";

		LocalTime resultTime = TimeConverter.getLocalTimeFromString(time);

		assertThat(resultTime.getHour()).isEqualTo(11);
		assertThat(resultTime.getMinute()).isEqualTo(11);
	}
}
