package com.studyhub.kartei.adapter.aspects;

import com.studyhub.kartei.adapter.web.exception.KarteikarteDoesNotExistException;
import com.studyhub.kartei.service.application.KarteikarteService;
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
class KarteikarteAspectTest {

	@Mock
	private KarteikarteService karteikarteService;

	@Mock
	private ProceedingJoinPoint joinPoint;

	@InjectMocks
	private KarteikarteAspect karteikarteAspect;

	@BeforeEach
	void setUp() {
		reset(karteikarteService);
	}

	static class DummyClass {
		@CheckKarteikarteExists
		public void dummyMethod(String karteFachId) {
		}
	}

	@Test
	@DisplayName("Wenn die Karte existiert, wird proceed() aufgerufen")
	void shouldProceedWhenKarteikarteExists() throws Throwable {
		String karteFachId = UUID.randomUUID().toString();
		when(karteikarteService.karteNotExistsById(karteFachId)).thenReturn(false);
		when(joinPoint.proceed()).thenReturn("Success");

		Object result = karteikarteAspect.checkIfExists(joinPoint, karteFachId);

		verify(joinPoint, times(1)).proceed();
		assertNotNull(result);
		assertEquals("Success", result);
	}

	@Test
	@DisplayName("Wenn die Karte nicht existiert, wird eine Exception geworfen")
	void shouldThrowExceptionWhenKarteikarteDoesNotExist() throws Throwable {
		String karteId = UUID.randomUUID().toString();
		when(karteikarteService.karteNotExistsById(karteId)).thenReturn(true);

		KarteikarteDoesNotExistException exception = assertThrows(
				KarteikarteDoesNotExistException.class,
				() -> karteikarteAspect.checkIfExists(joinPoint, karteId)
		);

		assertEquals("Karteikarte mit id: '%s' existiert nicht.".formatted(karteId), exception.getMessage());
		verify(joinPoint, never()).proceed();
	}
}