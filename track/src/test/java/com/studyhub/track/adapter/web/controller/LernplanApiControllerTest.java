package com.studyhub.track.adapter.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.studyhub.track.adapter.security.SecurityConfig;
import com.studyhub.track.adapter.web.controller.api.LernplanApiController;
import com.studyhub.track.application.JWTService;
import com.studyhub.track.application.service.LernplanAktivierungsService;
import com.studyhub.track.application.service.LernplanService;
import com.studyhub.track.domain.model.lernplan.Lernplan;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import java.util.List;
import static com.studyhub.track.util.LernplanMother.initFullLernplan;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(SecurityConfig.class)
@WebMvcTest(LernplanApiController.class)
@ActiveProfiles("application-dev.yaml")
public class LernplanApiControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockitoBean
	LernplanService lernplanService;

	@MockitoBean
	LernplanAktivierungsService lernplanAktivierungsService;

	@MockitoBean
	JWTService jwtService;

	@Autowired
	private ObjectMapper objectMapper;

	@Nested
	class UserNotAuthenticated {
		@Test
		@DisplayName("Alle API Endpoints sind für nciht authentifizierte User nicht erreichbar")
		void test_1() throws Exception {
			mockMvc.perform(get("/api/plan/v1/get-lernplan-by-id/123e4567-e89b-12d3-a456-426614174000"))
					.andExpect(status().isForbidden());

			mockMvc.perform(get("/api/plan/v1/get-all-lernplaene"))
					.andExpect(status().isForbidden());

			mockMvc.perform(get("/api/plan/v1/create"))
					.andExpect(status().isForbidden());

			mockMvc.perform(get("/api/plan/v1/get-active-lernplan"))
					.andExpect(status().isForbidden());

			mockMvc.perform(get("/api/plan/v1/delete-lernplan/123e4567-e89b-12d3-a456-426614174000"))
					.andExpect(status().isForbidden());

			mockMvc.perform(get("/api/plan/v1/set-active-lernplan/123e4567-e89b-12d3-a456-426614174000"))
					.andExpect(status().isForbidden());

			mockMvc.perform(get("/api/plan/v1/api/plan/v1/has-lernplan"))
					.andExpect(status().isForbidden());

			mockMvc.perform(get("/api/plan/v1/api/plan/v1/is-today-planned"))
					.andExpect(status().isForbidden());

			mockMvc.perform(get("/api/plan/v1/api/plan/v1/bearbeite-lernpla"))
					.andExpect(status().isForbidden());
		}
	}

	@Nested
	@WithMockUser(username = "testuser")
	class UserAuthenticated {
		@Test
		@DisplayName("/get-lernplan-by-id/{lernplanId} gibt 200 und den Lernplan zurueck, wenn Lernplan existiert")
		void test_1() throws Exception {
			Lernplan lernplan = initFullLernplan();
			when(lernplanService.findByFachId(any())).thenReturn(lernplan);
			mockMvc.perform(authorizedParamGet("/api/plan/v1/get-lernplan-by-id/{id}", lernplan.getFachId().toString()))
					.andExpect(status().isOk())
					.andExpect(content().json(objectMapper.writeValueAsString(lernplan)));
		}

		@Test
		@DisplayName("/get-lernplan-by-id/{lernplanId} gibt 404 zurueck, wenn Lernplan existiert")
		void test_2() throws Exception {
			when(lernplanService.findByFachId(any())).thenReturn(null);
			mockMvc.perform(get("/api/plan/v1/get-lernplan-by-id/123e4567-e89b-12d3-a456-426614174000"))
					.andExpect(status().isNotFound());
		}

		@Test
		@DisplayName("/get-all-lernplaene gibt 200 und eine Liste aller Lernpläne des Users")
		void test_3() throws Exception {
			when(jwtService.extractUsernameFromHeader(any())).thenReturn("testuser");
			List<Lernplan> lernplaene = List.of(initFullLernplan());
			when(lernplanService.findAllLernplaeneByUsername("testuser")).thenReturn(lernplaene);

			mockMvc.perform(authorizedGet("/api/plan/v1/get-all-lernplaene"))
					.andExpect(status().isOk())
					.andExpect(content().json(objectMapper.writeValueAsString(lernplaene)));
		}
	}

	private RequestBuilder authorizedGet(String url) {
		return get(url).header("Authorization", "Bearer dummyAuthToken");
	}

	private RequestBuilder authorizedParamGet(String url, String param) {
		return get(url, param).header("Authorization", "Bearer dummyAuthToken");
	}
}
