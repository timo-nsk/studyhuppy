package com.studyhub.track.adapter.web.validation;

import com.studyhub.track.adapter.web.ModulForm;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class KlausurDatumValidatorTest {

	static ConstraintValidatorContext context;
	static KlausurDatumValidator validator;

	@BeforeAll
	static void setup() {
		context = mock(ConstraintValidatorContext.class);
		validator = new KlausurDatumValidator();
	}

	@Test
	@DisplayName("Wenn eine Datum eingegeben wurde, aber keine Zeitangabe leer ist, wird false zurückgegeben")
	void test_1() {
		LocalDate now = LocalDate.now();
		String time = "";
		ModulForm modulForm = initFormWithKlausurDate(now, time);

		boolean valid = validator.isValid(modulForm, context);

		assertThat(valid).isFalse();
	}

	@Test
	@DisplayName("Wenn eine Datum eingegeben wurde, aber keine Zeitangabe 'null' ist, wird false zurückgegeben")
	void test_2() {
		LocalDate now = LocalDate.now();
		String time = null;
		ModulForm modulForm = initFormWithKlausurDate(now, time);

		boolean valid = validator.isValid(modulForm, context);

		assertThat(valid).isFalse();
	}

	@Test
	@DisplayName("Wenn eine Datum eingegeben wurde sowie eine Zeitangabe, wird true zurückgegeben")
	void test_3() {
		LocalDate now = LocalDate.now();
		String time = "11:20";
		ModulForm modulForm = initFormWithKlausurDate(now, time);

		boolean valid = validator.isValid(modulForm, context);

		assertThat(valid).isTrue();
	}

	@Test
	@DisplayName("Wenn kein Datum eingegeben wurde aber eine Zeiteingabe, wird false zurückgegeben")
	void test_4() {
		LocalDate now = null;
		String time = "11:20";
		ModulForm modulForm = initFormWithKlausurDate(now, time);

		boolean valid = validator.isValid(modulForm, context);

		assertThat(valid).isFalse();
	}

	@Test
	@DisplayName("Wenn kein Datum und eine leere Zeitangabe eingegeben wurde wird true zurückgegeben")
	void test_5() {
		LocalDate now = null;
		String time = "";
		ModulForm modulForm = initFormWithKlausurDate(now, time);

		boolean valid = validator.isValid(modulForm, context);

		assertThat(valid).isTrue();
	}

	@Test
	@DisplayName("Wenn kein Datum und keine Zeitangabe eingegeben wurde wird true zurückgegeben")
	void test_6() {
		LocalDate now = null;
		String time = null;
		ModulForm modulForm = initFormWithKlausurDate(now, time);

		boolean valid = validator.isValid(modulForm, context);

		assertThat(valid).isTrue();
	}

	private ModulForm initFormWithKlausurDate(LocalDate date, String time) {
		return new ModulForm(null, 0, 0, 0, date, time
				, null
				, null
				, null
				, null
				, null
				, null
				, null
				, null
				, null
				, null
				, null
				, null
				, null
				, null
				, null
				, null
				, null);
	}
}
