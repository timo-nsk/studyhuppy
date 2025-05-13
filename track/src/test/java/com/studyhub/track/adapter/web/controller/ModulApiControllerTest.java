package com.studyhub.track.adapter.web.controller;

import com.studyhub.jwt.JWTService;
import com.studyhub.track.adapter.web.ModulForm;
import com.studyhub.track.adapter.web.controller.api.ModulApiController;
import com.studyhub.track.application.service.ModulEventService;
import com.studyhub.track.application.service.ModulService;
import com.studyhub.track.domain.model.modul.Modul;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@WebMvcTest(ModulApiController.class)
public class ModulApiControllerTest {

	@Value("${maxModule}")
	private int MAX_MODULE;

	@MockitoBean
	private ModulService modulService;

	@MockitoBean
	private JWTService jwtService;

	@MockitoBean
	private ModulEventService modulEventService;

	@Autowired
	private ModulApiController modulApiController;

	@Test
	@DisplayName("Wenn ein neues Modul erstellt werden soll und der User noch welche erstellen kann, wird das Modul gespeichert")
	void test_01() {
		ModulForm modulForm = mock(ModulForm.class);
		MockHttpServletRequest request = new MockHttpServletRequest();
		Modul m = new Modul();

		when(jwtService.extractUsernameFromHeader(request)).thenReturn("peter");
		when(modulService.modulCanBeCreated("peter", MAX_MODULE)).thenReturn(true);
		when(modulForm.newModulFromFormData(any(ModulForm.class), anyInt())).thenReturn(m);

		modulApiController.newModule(modulForm, request);

		verify(modulService).saveNewModul(any(Modul.class));
	}

	@Test
	@DisplayName("Wenn ein neues Modul erstellt werden soll und der User schon die maximale Anzahl erstellter Module erreich hat, wird das Modul nicht gespeichert")
	void test_02() {
		ModulForm modulForm = mock(ModulForm.class);
		MockHttpServletRequest request = new MockHttpServletRequest();
		Modul m = new Modul();

		when(jwtService.extractUsernameFromHeader(request)).thenReturn("peter");
		when(modulService.modulCanBeCreated("peter", MAX_MODULE)).thenReturn(false);
		when(modulForm.newModulFromFormData(any(ModulForm.class), anyInt())).thenReturn(m);

		modulApiController.newModule(modulForm, request);

		verify(modulService, times(0)).saveNewModul(any(Modul.class));
	}
}

