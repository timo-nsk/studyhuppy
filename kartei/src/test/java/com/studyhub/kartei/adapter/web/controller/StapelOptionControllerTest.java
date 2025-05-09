package com.studyhub.kartei.adapter.web.controller;

import com.studyhub.kartei.adapter.web.controller.StapelOptionController;
import com.studyhub.kartei.service.application.StapelOptionService;
import com.studyhub.kartei.service.application.StapelService;
import com.studyhub.kartei.util.StapelMother;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StapelOptionController.class)
public class StapelOptionControllerTest {

	private final String RANDOM_UUID = UUID.randomUUID().toString();

	@Autowired
	MockMvc mvc;

	@MockitoBean
	StapelOptionService optionService;

	@MockitoBean
	StapelService stapelService;

	@Test
	@DisplayName("/set-bearbeiten/{karteiSetId} ist mit vorhandener Id aufrufbar und gibt die korrekte view zurück")
	void test_1() throws Exception {
		when(stapelService.findByFachId(anyString())).thenReturn(StapelMother.initSet());
		mvc.perform(get("/set-bearbeiten/{karteiSetId}", UUID.randomUUID().toString()))
				.andExpect(status().isOk())
				.andExpect(view().name("set-bearbeiten"));
	}

	@Test
	@DisplayName("req auf /set-bearbeiten/delete-set ist erfolreich und leitet wieder zurück auf die jeweilige Optionen-Seite")
	void test_2() throws Exception {
		mvc.perform(get("/set-bearbeiten/delete-set")
				.param("karteiSetId", RANDOM_UUID))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/kartei-uebersicht"));
	}

	@Test
	@DisplayName("req auf /set-bearbeiten/change-set-name ist erfolreich und leitet wieder zurück auf die jeweilige Optionen-Seite")
	void test_3() throws Exception {
		mvc.perform(post("/set-bearbeiten/change-set-name")
						.param("karteiSetId", RANDOM_UUID)
						.param("newSetName", "set"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/set-bearbeiten/%s".formatted(RANDOM_UUID)));
	}

	@Test
	@DisplayName("req auf /set-bearbeiten/reset-karteikarten ist erfolreich und leitet wieder zurück auf die jeweilige Optionen-Seite")
	void test_4() throws Exception {
		mvc.perform(get("/set-bearbeiten/reset-karteikarten")
						.param("karteiSetId", RANDOM_UUID))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/set-bearbeiten/%s".formatted(RANDOM_UUID)));
	}

	@Test
	@DisplayName("req auf /set-bearbeiten/delete-karteikarten ist erfolreich und leitet wieder zurück auf die jeweilige Optionen-Seite")
	void test_5() throws Exception {
		mvc.perform(get("/set-bearbeiten/delete-karteikarten")
						.param("karteiSetId", RANDOM_UUID))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/set-bearbeiten/%s".formatted(RANDOM_UUID)));
	}
}
