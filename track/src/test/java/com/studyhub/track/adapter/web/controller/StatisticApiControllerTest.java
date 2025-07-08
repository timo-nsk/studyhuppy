package com.studyhub.track.adapter.web.controller;

import com.studyhub.track.application.JWTService;
import com.studyhub.track.adapter.security.SecurityConfig;
import com.studyhub.track.adapter.web.controller.api.StatisticApiController;
import com.studyhub.track.application.service.ModulEventService;
import com.studyhub.track.application.service.ModulService;
import com.studyhub.track.util.ApiEndpointArgumentProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StatisticApiController.class)
@Import(SecurityConfig.class)
@ActiveProfiles("application-dev.yaml")
public class StatisticApiControllerTest {

	@Autowired
	MockMvc mvc;

	@MockitoBean
	ModulService modulService;

	@MockitoBean
	ModulEventService modulEventService;

	@MockitoBean
	JWTService jwtService;

	@ParameterizedTest
	@ArgumentsSource(ApiEndpointArgumentProvider.class)
	@DisplayName("Alle API-Endpunkte sind für nicht authentifizierte Benutzer zugänglich")
	void test_1(String endpoint) throws Exception {
		mvc.perform(get("/api/modul/v1/%s".formatted(endpoint)))
				.andExpect(status().isForbidden());
	}

	@ParameterizedTest
	@ArgumentsSource(ApiEndpointArgumentProvider.class)
	@DisplayName("API-Endpunkte sind für authentifizierte Benutzer zugänglich")
	@WithMockUser(username = "testuser", roles = "USER")
	void test_2(String endpoint) throws Exception {
		mvc.perform(get("/api/modul/v1/%s".formatted(endpoint)))
				.andExpect(status().isOk());
	}
}