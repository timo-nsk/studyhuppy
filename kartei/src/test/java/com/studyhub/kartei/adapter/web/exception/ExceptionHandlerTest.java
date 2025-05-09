package com.studyhub.kartei.adapter.web.exception;

import com.studyhub.kartei.service.application.KarteikarteService;
import com.studyhub.kartei.service.application.StapelService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class ExceptionHandlerTest {

	@Autowired
	private MockMvc mvc;

	@MockitoBean
	StapelService stapelService;

	@MockitoBean
	KarteikarteService karteikarteService;

	@Test
	@DisplayName("Ein Request auf /karte-bearbeiten/{karteiSetId}/{karteFachId} mit ungültiger STAPEL-ID " +
			"wirft Exception und leitet zur error-Seite")
	void test_1() throws Exception {
		String stapelId = UUID.randomUUID().toString();
		String karteId = UUID.randomUUID().toString();
		when(stapelService.stapelNotExists(anyString())).thenReturn(true);

		mvc.perform(get("/karte-bearbeiten/{karteiSetId}/{karteFachId}", stapelId, karteId))
				.andExpect(status().isNotFound())
				.andExpect(view().name("error"));
	}

	//@Disabled("Da iwrd nicht wirklich mit der Annotation gepüft, ob die Karte nicht da ist, es wird immer die Stapel-ID gepürft")
	@Test
	@DisplayName("Ein Request auf /karte-bearbeiten/{karteiSetId}/{karteFachId} mit ungültiger KARTE-ID " +
			"wirft Exception und leitet zur error-Seite")
	void test_2() throws Exception {
		String stapelId = UUID.randomUUID().toString();
		String karteId = UUID.randomUUID().toString();
		when(karteikarteService.karteNotExistsById(anyString())).thenReturn(true);


		mvc.perform(get("/karte-bearbeiten/{karteiSetId}/{karteFachId}", stapelId, karteId))
				.andExpect(status().isNotFound())
				.andExpect(view().name("error"));
	}

	@Test
	@DisplayName("Ein Request auf /bearbeite-karte/{karteiSetId}/{karteFachId} mit ungültiger STAPEL-ID " +
			"wirft Exception und leitet zur error-Seite")
	void test_3() throws Exception {
		String stapelId = UUID.randomUUID().toString();
		String karteId = UUID.randomUUID().toString();
		when(stapelService.stapelNotExists(anyString())).thenReturn(true);


		mvc.perform(post("/bearbeite-karte/{karteiSetId}/{karteFachId}", stapelId, karteId))
				.andExpect(status().isNotFound())
				.andExpect(view().name("error"));
	}

	@Test
	@DisplayName("Ein Request auf /bearbeite-karte/{karteiSetId}/{karteFachId} mit ungültiger KARTE-ID " +
			"wirft Exception und leitet zur error-Seite")
	void test_4() throws Exception {
		String stapelId = UUID.randomUUID().toString();
		String karteId = UUID.randomUUID().toString();
		when(karteikarteService.karteNotExistsById(anyString())).thenReturn(true);


		mvc.perform(post("/bearbeite-karte/{karteiSetId}/{karteFachId}", stapelId, karteId))
				.andExpect(status().isNotFound())
				.andExpect(view().name("error"));
	}

	@Test
	@DisplayName("Ein Request auf /lernen/{karteiSetId}/new-karte mit ungültiger STAPEL-ID " +
			"wirft Exception und leitet zur error-Seite")
	void test_5() throws Exception {
		String stapelId = UUID.randomUUID().toString();
		when(stapelService.stapelNotExists(anyString())).thenReturn(true);


		mvc.perform(get("/lernen/{karteiSetId}/new-karte", stapelId))
				.andExpect(status().isNotFound())
				.andExpect(view().name("error"));
	}

	@Test
	@DisplayName("Ein Request auf /create-karteikarte-normal/{karteiSetId} mit ungültiger STAPEL-ID " +
			"wirft Exception und leitet zur error-Seite")
	void test_6() throws Exception {
		String stapelId = UUID.randomUUID().toString();
		when(stapelService.stapelNotExists(anyString())).thenReturn(true);


		mvc.perform(post("/create-karteikarte-normal/{karteiSetId}", stapelId))
				.andExpect(status().isNotFound())
				.andExpect(view().name("error"));
	}

	@Test
	@DisplayName("Ein Request auf /lernen/{karteiSetId}/übersicht mit ungültiger STAPEL-ID " +
			"wirft Exception und leitet zur error-Seite")
	void test_7() throws Exception {
		String stapelId = UUID.randomUUID().toString();
		when(stapelService.stapelNotExists(anyString())).thenReturn(true);


		mvc.perform(get("/lernen/{karteiSetId}/übersicht", stapelId))
				.andExpect(status().isNotFound())
				.andExpect(view().name("error"));
	}
}
