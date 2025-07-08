package com.studyhub.track.service;

import com.studyhub.track.application.service.TimeConverter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.time.LocalTime;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class TimeConverterTest {

	private static TimeConverter tc;

	@BeforeAll
	public static void init() {
		tc = new TimeConverter();
	}

	@Nested
	class FormatValidation {
		@ParameterizedTest
		@CsvSource(value="00:00, 10:59, 23:59")
		@DisplayName("Tatsächlich valide Time-Strings werden true matched")
		void test_1(String time) {
			assertTrue(tc.isValidTimeFormat(time));
		}

		@ParameterizedTest
		@CsvSource(value="-00:60, 24:59, -23:-59, 0:0, 2222, null, ''")
		@DisplayName("Tatsächlich invalide Time-Strings werden false matched")
		void test_2(String time) {
			assertFalse(tc.isValidTimeFormat(time));
		}
	}

	@Nested
	class ValidConversion {
		@ParameterizedTest
		@CsvSource(value="01:30, 5400")
		@DisplayName("1 Stunde und 30 Minuten werden in 5400 Sekunden konvertiert")
		void test_1(String time, int expected) {
			int converted = tc.timeToSeconds(time);
			assertThat(converted).isEqualTo(expected);
		}

		@Test
		@DisplayName("Der Zeit-String '11:11' wird korrekt in ein LocalDate umgewandelt")
		void test_2() {
			String time = "11:11";

			LocalTime resultTime = tc.getLocalTimeFromString(time);

			assertThat(resultTime.getHour()).isEqualTo(11);
			assertThat(resultTime.getMinute()).isEqualTo(11);
		}
	}

	@Nested
	class InvalidConversion {
		@ParameterizedTest
		@CsvSource(value="25:60, 23:-50, -23:49, -23:-49, null, ''")
		@DisplayName("Wenn invalide Time-Strings in Sekunden konvertiert werden sollen, wird eine Exception geworfen")
		void test_1(String time) {
			IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> tc.timeToSeconds(time));
			assertEquals("hh and mm out of range", thrown.getMessage());
		}

		@ParameterizedTest
		@CsvSource(value="25:60, 23:-50, -23:49, -23:-49, null, ''")
		@DisplayName("Für ungültige Time-String wird kein LocalTime-Objekt erstellt, sondern eine Exception geworfen")
		void test_2(String time) {
			IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> tc.getLocalTimeFromString(time));
			assertEquals("hh and mm out of range", thrown.getMessage());
		}
	}
}