package com.studyhub.track.adapter.web.controller;

import com.studyhub.track.adapter.security.SecurityConfig;
import com.studyhub.track.adapter.web.FachIdRequest;
import com.studyhub.track.application.service.ModulEventService;
import com.studyhub.track.application.service.ModulService;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled
@WebMvcTest(ModulApiController.class)
public class ModulApiControllerTest {

	@Autowired
	MockMvc mvc;

	@MockitoBean
	ModulService modulService;

	@MockitoBean
	ModulEventService modulEventService;

	@Test
	@DisplayName("Post-Request auf /api/get-seconds ist erfolgreich")
	void test_01() throws Exception {
		FachIdRequest request = new FachIdRequest(UUID.randomUUID().toString());

		mvc.perform(post("/api/get-seconds")
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(request))
						.cookie(new Cookie("auth_token", "token")))
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName(" Post-Request auf /api/update ist erfolgreich")
	void test_02() throws Exception {
		//TODO: Implement test, works manually
	}
}
