package com.studyhub.kartei.adapter.web.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
public class FooterControllerTest {

	@Autowired
	private MockMvc mvc;

	@Test
	@DisplayName("Alle Websites sind für nicht-authentifizierte User nicht sichtbar.")
	void test_1() throws Exception {
		mvc.perform(get("/impressum"))
				.andExpect(status().is3xxRedirection());
		mvc.perform(get("/datenschutz"))
				.andExpect(status().is3xxRedirection());
		mvc.perform(get("/kontakt"))
				.andExpect(status().is3xxRedirection());
	}

	@Test
	@WithMockUser(username = "testuser")
	@DisplayName("Alle Websites sind für authentifizierte User sichtbar.")
	void test_2() throws Exception {
		mvc.perform(get("/impressum")).andExpect(status().isOk());
		mvc.perform(get("/datenschutz")).andExpect(status().isOk());
		mvc.perform(get("/kontakt")).andExpect(status().isOk());
	}
}
