package com.studyhub.kartei.adapter.aspects;

import com.studyhub.kartei.adapter.web.exception.StapelDoesNotExistException;
import com.studyhub.kartei.service.application.StapelService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StapelAspectTest {

	@Mock
	private StapelService stapelService;

	@Mock
	private ProceedingJoinPoint joinPoint;

	@InjectMocks
	private StapelAspect stapelAspect;

	@BeforeEach
	void setUp() {
		reset(stapelService);
	}

	static class DummyClass {
		@CheckKarteikarteExists
		public void dummyMethod(String karteFachId) {
		}
	}

	@Test
	@DisplayName("Wenn der Stapel existiert, wird proceed() aufgerufen")
	void test_1() throws Throwable {
		// Arrange
		String karteFachId = UUID.randomUUID().toString();
		when(stapelService.stapelNotExists(karteFachId)).thenReturn(false);
		when(joinPoint.proceed()).thenReturn("Success");
		// Act
		Object result = stapelAspect.checkIfExists(joinPoint, karteFachId);

		// Assert
		verify(joinPoint, times(1)).proceed();  // Sollte fortfahren, wenn die Karte existiert
		assertNotNull(result);
		assertEquals("Success", result);
	}

	@Test
	@DisplayName("Wenn der Stapel nnicht existiert, wird eine Exception geworfen")
	void test_2() throws Throwable {
		String karteFachId = UUID.randomUUID().toString();
		when(stapelService.stapelNotExists(karteFachId)).thenReturn(true);

		StapelDoesNotExistException exception = assertThrows(
				StapelDoesNotExistException.class,
				() -> stapelAspect.checkIfExists(joinPoint, karteFachId)
		);

		assertEquals("Stapel mit id: '%s' nicht gefunden.".formatted(karteFachId), exception.getMessage());
		verify(joinPoint, never()).proceed();
	}
}