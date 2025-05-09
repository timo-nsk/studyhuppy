package com.studyhub.kartei.adapter.web.controller;

import com.studyhub.kartei.adapter.web.form.NewKarteikarteForm;
import com.studyhub.kartei.domain.model.Karteikarte;
import com.studyhub.kartei.service.application.KarteikarteService;
import com.studyhub.kartei.service.application.StapelService;
import com.studyhub.kartei.util.KarteikarteMother;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
public class KarteikarteBearbeitenControllerTest {

	@Autowired
	MockMvc mvc;

	@MockitoBean
	StapelService stapelService;

	@MockitoBean
	KarteikarteService karteikarteService;

	@Test
	@DisplayName("/karte-bearbeiten/{karteiSetId}/{karteFachId} ist erfolgreich aufrufbar und gibt die korrekte view zurück")
	@WithMockUser(username = "testuser")
	void test_1() throws Exception {
		String stapelId = UUID.randomUUID().toString();
		String karteId = UUID.randomUUID().toString();
		Karteikarte karte = KarteikarteMother.newKarteikarte("a", "b");
		when(stapelService.findKarteikarteByFachId(stapelId, karteId)).thenReturn(karte);

		mvc.perform(get("/karte-bearbeiten/{karteiSetId}/{karteFachId}", stapelId, karteId))
				.andExpect(status().isOk())
				.andExpect(view().name("karte-bearbeiten"));
	}

	@Test
	@DisplayName("/karte-bearbeiten/{karteiSetId}/{karteFachId} befüllt das Model mit allen benötigten Daten")
	@WithMockUser(username = "testuser")
	void test_2() throws Exception {
		String stapelId = UUID.randomUUID().toString();
		String karteId = UUID.randomUUID().toString();
		Karteikarte karte = KarteikarteMother.newKarteikarte("a", "b");
		NewKarteikarteForm form = new NewKarteikarteForm(karte.getFrage(), karte.getAntwort(), karte.getNotiz(), karte.getFrageTyp().toString());
		when(stapelService.findKarteikarteByFachId(stapelId, karteId)).thenReturn(karte);

		mvc.perform(get("/karte-bearbeiten/{karteiSetId}/{karteFachId}", stapelId, karteId))
				.andExpect(model().attribute("newKarteikarteForm", form))
				.andExpect(model().attribute("fragetypChoice", form.frageTyp()))
				.andExpect(model().attribute("karteiSetId", stapelId))
				.andExpect(model().attribute("karteFachId", karteId));
	}

	@Test
	@DisplayName("Request auf /bearbeite-karte/{karteiSetId}/{karteFachId} leitet den Editier-Prozess erfolgreich ein")
	@WithMockUser(username = "testuser")
	void test_3() throws Exception {
		String stapelId = UUID.randomUUID().toString();
		NewKarteikarteForm form = new NewKarteikarteForm("a", "b", "n", "NORMAL");
		Karteikarte karte = form.toKarteikarte();
		when(karteikarteService.editNormalKarteikarte(anyString(), anyString(), any(Karteikarte.class)))
				.thenReturn(true);

		mvc.perform(post("/bearbeite-karte/{karteiSetId}/{karteFachId}", stapelId, karte.getFachId().toString())
						.param("karteiSetId", stapelId)
						.param("karteFachId", karte.getFachId().toString())
						.param("frage", form.frage())
						.param("antwort", form.antwort())
						.param("notiz", form.notiz())
						.param("frageTyp", form.frageTyp()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/set-bearbeiten/%s".formatted(stapelId)));
	}

	@Test
	@DisplayName("Wenn der Editier-Prozess Request auf /bearbeite-karte/{karteiSetId}/{karteFachId} nicht erfolgreich ist, " +
			"wird eine Exception geworfen und die view error zurückgegeben")
	@WithMockUser(username = "testuser")
	void test_4() throws Exception {
		String stapelId = UUID.randomUUID().toString();
		NewKarteikarteForm form = new NewKarteikarteForm("a", "b", "n", "NORMAL");
		Karteikarte karte = form.toKarteikarte();
		when(karteikarteService.editNormalKarteikarte(anyString(), anyString(), any(Karteikarte.class)))
				.thenReturn(false);

		mvc.perform(post("/bearbeite-karte/{karteiSetId}/{karteFachId}", stapelId, karte.getFachId().toString())
						.param("karteiSetId", stapelId)
						.param("karteFachId", karte.getFachId().toString())
						.param("frage", form.frage())
						.param("antwort", form.antwort())
						.param("notiz", form.notiz())
						.param("frageTyp", form.frageTyp()))
				.andExpect(status().isBadRequest())
				.andExpect(view().name("error"));
	}
}
