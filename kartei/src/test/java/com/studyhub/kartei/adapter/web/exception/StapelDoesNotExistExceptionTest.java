package com.studyhub.kartei.adapter.web.exception;

import com.studyhub.kartei.adapter.web.exception.StapelDoesNotExistException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StapelDoesNotExistExceptionTest {

	@Test
	@DisplayName("Korrekte Instanziierung von StapelDoesNotExistException")
	void test_1() {
		String errorMessage = "Stapel existiert nicht!";
		StapelDoesNotExistException exception = new StapelDoesNotExistException(errorMessage);

		assertEquals(errorMessage, exception.getMessage());
	}

	@Test
	@DisplayName("Exception wird mit message geworfen")
	void test_2() {
		Exception exception = assertThrows(StapelDoesNotExistException.class, () -> {
			throw new StapelDoesNotExistException("Test Message");
		});

		assertEquals("Test Message", exception.getMessage());
	}
}
