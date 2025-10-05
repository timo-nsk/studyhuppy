package com.studyhub.track.actuator;

import com.studyhub.track.adapter.actuator.HealthController;
import com.studyhub.track.adapter.security.SecurityConfig;
import com.studyhub.track.application.service.ModulService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HealthController.class)
@ActiveProfiles("application-dev.yaml")
@Import(SecurityConfig.class)
public class HealthControllerTest {

	@MockitoBean
	ModulService modulService;

	@Autowired
	MockMvc mockMvc;

	@Test
	@DisplayName("/get-db-health ist erreichbar und returned den String 'true', wenn die Datenbank erreichbar ist")
	void test_1() throws Exception {
		when(modulService.isModulDbHealthy()).thenReturn(true);
		mockMvc.perform(get("/api/modul/v1/get-db-health"))
				.andExpect(status().isOk())
				.andExpect(content().string("true"));
	}

	@Test
	@DisplayName("/get-db-health ist erreichbar und returned den String 'false', wenn die Datenbank nicht erreichbar ist")
	void test_2() throws Exception {
		when(modulService.isModulDbHealthy()).thenReturn(false);
		mockMvc.perform(get("/api/modul/v1/get-db-health"))
				.andExpect(status().isOk())
				.andExpect(content().string("false"));
	}

}
