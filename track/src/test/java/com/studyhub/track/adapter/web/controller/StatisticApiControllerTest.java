package com.studyhub.track.adapter.web.controller;

import com.studyhub.track.adapter.security.JwtAuthFilter;
import com.studyhub.track.adapter.security.RedirectEntryPoint;
import com.studyhub.track.adapter.security.SecurityConfig;
import com.studyhub.track.adapter.web.controller.api.StatisticApiController;
import com.studyhub.track.application.service.ModulEventService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Disabled
@WebMvcTest(StatisticApiController.class)
@Import(SecurityConfig.class)
public class StatisticApiControllerTest {

	@Autowired
	MockMvc mvc;

	@MockitoBean
	ModulEventService modulEventService;

	@MockitoBean
	RedirectEntryPoint redirectEntryPoint;

	@MockitoBean
	JwtAuthFilter filter;

	@Test
	@DisplayName(" Get-Request auf /statistics/api/stats ist erfolgreich")
	void test_01() throws Exception {

		mvc.perform(get("/statistics/api/stats")).andExpect(status().isOk());
	}

}
