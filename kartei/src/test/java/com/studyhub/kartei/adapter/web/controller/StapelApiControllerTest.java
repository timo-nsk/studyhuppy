package com.studyhub.kartei.adapter.web.controller;

import com.studyhub.kartei.domain.model.Stapel;
import com.studyhub.kartei.service.application.StapelService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StapelApiController.class)
public class StapelApiControllerTest {

	@Autowired
	MockMvc mvc;

	@MockitoBean
	StapelService stapelService;

	@Test
	@DisplayName("Ein Post-Request auf /api/create-stapel ist erfolgreich und speichert aus den Daten des Request-Body einen neuen Stapel")
	public void test_1() throws Exception {
		UUID uuid = UUID.randomUUID();
		CreateNewStapelRequest req = mock(CreateNewStapelRequest.class);
		String reqContent = """
				            {
				                "modulFachId": "%s",
				                "setName": "neuesSet",
				                "beschreibung": "eineBeschreibung",
				                "lernIntervalle": "2m,2h,4d",
				                "username": "peter978"
				            }
                            """.formatted(uuid);
		when(req.toNewStapel()).thenReturn(new Stapel(uuid, "neuesSet", "eineBeschreibung", "2m,2h,4d", "username"));

		mvc.perform(post("/api/create-stapel")
						.contentType(MediaType.APPLICATION_JSON)
						.content(reqContent))
				.andExpect(status().isCreated());

		verify(stapelService).saveSet(any(Stapel.class));
	}

	@Test
	@DisplayName("Ein Get-Request auf /api/get-faelligeKarten ist erfolgreich und gibt die angefragte Map zurück")
	public void test_2() throws Exception {
		Map<String, Integer> responseMap = Map.of("stapel1", 1, "stapel2", 2, "stapel3", 3);

		when(stapelService.getAnzahlFaelligeKartenForEachStapel(any(LocalDateTime.class))).thenReturn(responseMap);

		mvc.perform(get("/api/get-faelligeKarten"))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.['stapel1']").value(1))
				.andExpect(jsonPath("$.['stapel2']").value(2))
				.andExpect(jsonPath("$.['stapel3']").value(3));
	}


}

