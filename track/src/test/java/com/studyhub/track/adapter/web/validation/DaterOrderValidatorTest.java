package com.studyhub.track.adapter.web.validation;

import com.studyhub.track.adapter.web.SemesterForm;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

public class DaterOrderValidatorTest {

	static ConstraintValidatorContext context;
	static DaterOrderValidator validator;
	static ConstraintValidatorContextSetter cs;

	@BeforeAll
	static void setup() {
		context = mock(ConstraintValidatorContext.class);
		cs = mock(ConstraintValidatorContextSetter.class);
		validator = new DaterOrderValidator(cs);
	}

	@Test
	@DisplayName("Wenn das Semesterbeginn-Datum nach den Vorlesungbeginn-Datum startet wird false zurückgegeben")
	void test_01() {
		doNothing().when(cs).contextSetter(any(), any(), any());
		SemesterForm form = initSemesterFormWithDates(
				dateOf(10,5,2024),
				dateOf(10,9, 2024),
				dateOf(1,5,2024),
				dateOf(10,8,2024));

		boolean valid = validator.isValid(form, context);

		assertThat(valid).isFalse();
	}

	@Test
	@DisplayName("Wenn das Semesterbeginn-Datum vor dem Vorlesungbeginn-Datum startet wird true zurückgegeben")
	void test_02() {
		doNothing().when(cs).contextSetter(any(), any(), any());
		SemesterForm form = initSemesterFormWithDates(
				dateOf(10,5,2024),
				dateOf(10,9, 2024),
				dateOf(15,5,2024),
				dateOf(10,8,2024));

		boolean valid = validator.isValid(form, context);

		assertThat(valid).isTrue();
	}

	@Test
	@DisplayName("Wenn der Vorlesungsbeginn nach dem Semesterende startet wird false zurückgegeben")
	void test_03() {
		doNothing().when(cs).contextSetter(any(), any(), any());
		SemesterForm form = initSemesterFormWithDates(
				dateOf(10,5,2024),
				dateOf(10,9, 2024),
				dateOf(15,9,2024),
				dateOf(10,8,2024));

		boolean valid = validator.isValid(form, context);

		assertThat(valid).isFalse();
	}

	@Test
	@DisplayName("Wenn der Vorlesungsbeginn vor dem Semesterende startet wird true zurückgegeben")
	void test_04() {
		doNothing().when(cs).contextSetter(any(), any(), any());
		SemesterForm form = initSemesterFormWithDates(
				dateOf(10,5,2024),
				dateOf(10,9, 2024),
				dateOf(15,5,2024),
				dateOf(10,8,2024));

		boolean valid = validator.isValid(form, context);

		assertThat(valid).isTrue();
	}

	@Test
	@DisplayName("Wenn das Semesterende vor dem Vorlesungende ist wird false zurückgegeben")
	void test_05() {
		doNothing().when(cs).contextSetter(any(), any(), any());
		SemesterForm form = initSemesterFormWithDates(
				dateOf(10,5,2024),
				dateOf(9,9, 2024),
				dateOf(15,5,2024),
				dateOf(10,9,2024));

		boolean valid = validator.isValid(form, context);

		assertThat(valid).isFalse();
	}

	@Test
	@DisplayName("Wenn das Semesterende nach dem Vorlesungende ist wird true zurückgegeben")
	void test_06() {
		doNothing().when(cs).contextSetter(any(), any(), any());
		SemesterForm form = initSemesterFormWithDates(
				dateOf(10,5,2024),
				dateOf(10,9, 2024),
				dateOf(15,5,2024),
				dateOf(5,9,2024));

		boolean valid = validator.isValid(form, context);

		assertThat(valid).isTrue();
	}

	@Test
	@DisplayName("Wenn das Semesterende = Semesterbeginn ist wird false zurückgegeben")
	void test_07() {
		doNothing().when(cs).contextSetter(any(), any(), any());
		SemesterForm form = initSemesterFormWithDates(
				dateOf(10,5,2024),
				dateOf(10,5, 2024),
				dateOf(15,5,2024),
				dateOf(5,9,2024));

		boolean valid = validator.isValid(form, context);

		assertThat(valid).isFalse();
	}

	@Test
	@DisplayName("Wenn der Vorlesungbeginn = Vorlesungende ist wird false zurückgegeben")
	void test_08() {
		doNothing().when(cs).contextSetter(any(), any(), any());
		SemesterForm form = initSemesterFormWithDates(
				dateOf(10,5,2024),
				dateOf(10,9, 2024),
				dateOf(15,5,2024),
				dateOf(15,5,2024));

		boolean valid = validator.isValid(form, context);

		assertThat(valid).isFalse();
	}

	@Test
	@DisplayName("Wenn das Vorlesungende vor dem Semesterbeginn liegt wird false zurückgegeben")
	void test_09() {
		doNothing().when(cs).contextSetter(any(), any(), any());
		SemesterForm form = initSemesterFormWithDates(
				dateOf(10,5,2024),
				dateOf(10,9, 2024),
				dateOf(15,5,2024),
				dateOf(9,5,2024));

		boolean valid = validator.isValid(form, context);

		assertThat(valid).isFalse();
	}

	public SemesterForm initSemesterFormWithDates(LocalDate semesterBeginn,
	                                              LocalDate semesterEnde,
	                                              LocalDate vlBeginn,
	                                              LocalDate vlEnde) {
		return new SemesterForm(1, semesterBeginn, semesterEnde, vlBeginn, vlEnde, null, "somedata");
	}

	public LocalDate dateOf(int day, int month, int year) {
		return LocalDate.of(year, month, day);
	}
}
