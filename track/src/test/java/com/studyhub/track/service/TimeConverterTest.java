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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TimeConverterTest {

	private static TimeConverter tc;

	@BeforeAll
	public static void init() {
		tc = new TimeConverter();
	}

	@Nested
	class ValidConversion {
		@ParameterizedTest
		@CsvSource(value="01:30, 5400")
		void test_01(String time, int expected) {
			int converted = tc.timeToSeconds(time);
			assertThat(converted).isEqualTo(expected);
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

	@Nested
	class InvalidConversion {
		@ParameterizedTest
		@CsvSource(value="25:60, 23:-50, -23:49, -23:-49")
		void test_01(String time) {
			IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> tc.timeToSeconds(time));
			assertEquals("hh and mm out of range", thrown.getMessage());
		}
	}
}
