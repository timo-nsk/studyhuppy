package com.studyhub.kartei.adapter.web.controller;

import com.studyhub.kartei.adapter.web.exception.StapelDoesNotExistException;
import com.studyhub.kartei.domain.model.FrageTyp;
import com.studyhub.kartei.domain.model.Stapel;
import com.studyhub.kartei.service.application.KarteikarteService;
import com.studyhub.kartei.service.application.StapelService;
import com.studyhub.kartei.service.application.UpdateInfo;
import com.studyhub.kartei.util.StapelMother;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(KarteikarteLernenController.class)
public class KarteikarteLernenControllerTest {

	@Autowired
	MockMvc mvc;

	@MockitoBean
	StapelService stapelService;

	@MockitoBean
	KarteikarteService karteikarteService;

	@Test
	@DisplayName("/lernen/{karteiSetId}/übersicht wird mit beliebiger karteiSetId erfolgreich aufgerufen und gibt die korrekte view zurück")
	void test_1() throws Exception {
		String setId = "7c92d840-ab7c-41e1-809f-08899893f186";
		String karteId = "8c92d840-ab7c-41e1-809f-08899893f186";
		Stapel set = mock(Stapel.class);

		when(stapelService.findByFachId(setId)).thenReturn(set);
		when(set.karteikartenAvailable()).thenReturn(true);
		when(set.getFirstKarteikarteId()).thenReturn(karteId);
		when(set.getFachId()).thenReturn(UUID.fromString(setId));
		when(set.getName()).thenReturn("Mathematik");

		mvc.perform(get("/lernen/{karteiSetId}/übersicht", setId))
				.andExpect(status().isOk())
				.andExpect(view().name("set-übersicht"))
				.andExpect(model().attribute("karteikartenAvailable", true))
				.andExpect(model().attribute("setFachId", setId))
				.andExpect(model().attribute("setName", "Mathematik"));
	}

	@Test
	@DisplayName("/lernen/{karteiSetId}/übersicht ist mit nicht existierendem Karteiset nicht erreichbar und gibt die error-view zurück")
	void test_2() throws Exception {
		String karteiSetId = UUID.randomUUID().toString();
		when(stapelService.stapelNotExists(karteiSetId)).thenThrow(StapelDoesNotExistException.class);

		mvc.perform(get("/lernen/{karteiSetId}/übersicht", karteiSetId))
				.andExpect(status().is4xxClientError())
				.andExpect(view().name("error"));
	}

	@Test
	@DisplayName("/lernen/{karteiSetId}/start ist erreichbar und gibt die korrekte view wieder")
	void test_3() throws Exception {
		String karteiSetId = UUID.randomUUID().toString();
		Stapel set = StapelMother.initSet();

		when(stapelService.findByFachId(anyString())).thenReturn(set);
		when(stapelService.maybeGetHttpSessionForStapel(any(HttpSession.class), any(Stapel.class)))
				.thenReturn(set);
		when(stapelService.isLastKarteikarteReached(anyInt(), anyInt()))
				.thenReturn(false);

		mvc.perform(get("/lernen/{karteiSetId}/start", karteiSetId)
						.sessionAttr("currentSet", set)
						.cookie(new Cookie("sessionKarteIndex", "0")))
				.andExpect(status().isOk())
				.andExpect(view().name("set-lernen"))
				.andExpect(model().attribute("currentKarte", set.getKarteikarten().get(0)))
				.andExpect(model().attribute("stapelId", karteiSetId))
				.andExpect(cookie().exists("sessionKarteIndex"));
	}

	@Test
	@DisplayName("Wenn die letzte Karteikarte des Sets gelernt wurde, wird auf /lernen/{karteiSetId}/ende redirected")
	void test_4() throws Exception {
		String karteiSetId = UUID.randomUUID().toString();
		Stapel set = StapelMother.initSet();

		when(stapelService.findByFachId(anyString())).thenReturn(set);
		when(stapelService.maybeGetHttpSessionForStapel(any(HttpSession.class), any(Stapel.class)))
				.thenReturn(set);
		when(stapelService.isLastKarteikarteReached(anyInt(), anyInt()))
				.thenReturn(true);

		mvc.perform(get("/lernen/{karteiSetId}/start", karteiSetId)
						.sessionAttr("currentSet", set)
						.cookie(new Cookie("sessionKarteIndex", String.valueOf(set.getKarteikarten().size()))))
				.andExpect(status().is3xxRedirection())
				.andExpect(cookie().exists("sessionKarteIndex"))
				.andExpect(redirectedUrl("/lernen/%s/ende".formatted(karteiSetId)));
	}

	@Test
	@DisplayName("Zweimaliger Aufruf nach /lernen/{karteiSetId}/start würde das Cookie 'sessionKarteIndex' auf value=2 updaten")
	void test_6() throws Exception {
		String setId = "7c92d840-ab7c-41e1-809f-08899893f186";
		Stapel set = StapelMother.initSetWithALotKarteikarten();
		when(stapelService.findByFachId(anyString())).thenReturn(set);
		when(stapelService.maybeGetHttpSessionForStapel(any(HttpSession.class), any(Stapel.class)))
				.thenReturn(set);
		when(stapelService.isLastKarteikarteReached(anyInt(), anyInt()))
				.thenReturn(false);

		MvcResult result1 = mvc.perform(get("/lernen/{karteiSetId}/start", setId)
						.cookie(new Cookie("sessionKarteIndex", "0")))
				.andExpect(status().isOk())
				.andExpect(cookie().value("sessionKarteIndex", "1"))
				.andReturn();

		String updatedCookieValue = result1.getResponse().getCookie("sessionKarteIndex").getValue();

		mvc.perform(get("/lernen/{karteiSetId}/start", setId)
						.cookie(new Cookie("sessionKarteIndex", updatedCookieValue)))
				.andExpect(status().isOk())
				.andExpect(cookie().value("sessionKarteIndex", "2"))
				.andReturn();
	}

	@Test
	@DisplayName("/lernen/{karteiSetId}/ende kann erfolgreich aufgerufen werden und gibt die korrekte view zurück")
	void test_7() throws Exception{
		mvc.perform(get("/lernen/{karteiSetId}/ende", UUID.randomUUID().toString()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/kartei-uebersicht"));
	}

	@Test
	@DisplayName("/update-karteikarte kann erfolgreich aufgerufen werden und leitet den Update-Prozess ein")
	void test_8() throws Exception{
		String stapelId = UUID.randomUUID().toString();
		when(karteikarteService.updateKarteikarteForNextReview(any(UpdateInfo.class))).thenReturn(true);
		mvc.perform(post("/update-karteikarte")
						.param("stapelId", stapelId)
						.param("karteId", UUID.randomUUID().toString())
						.param("schwierigkeit", FrageTyp.NORMAL.toString()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/lernen/%s/start".formatted(stapelId)));
	}

	@Test
	@DisplayName("Wenn der Update-Prozess auf /update-karteikarte fehlschlägt, wird eine Excepption geworfen und auf die error-Seite geleitet")
	void test_9() throws Exception{
		String stapelId = UUID.randomUUID().toString();
		when(karteikarteService.updateKarteikarteForNextReview(any(UpdateInfo.class))).thenReturn(false);
		mvc.perform(post("/update-karteikarte")
						.param("stapelId", stapelId)
						.param("karteId", UUID.randomUUID().toString())
						.param("schwierigkeit", FrageTyp.NORMAL.toString()))
				.andExpect(status().isBadRequest())
				.andExpect(view().name("error"));
	}

	@Test
	@DisplayName("Wenn ein Request auf /lernen/{karteiSetId}/übersicht mit einet Stapel-Id eintrifft und er Stapel nicht existiert," +
			"wird eine Exception geworfen und die error-View zurückgegeben")
	void test_10() throws Exception {
		when(stapelService.stapelNotExists(anyString())).thenReturn(true);

		mvc.perform(get("/lernen/{karteiSetId}/übersicht", UUID.randomUUID().toString()))
				.andExpect(status().isNotFound())
				.andExpect(view().name("error"));
	}
}