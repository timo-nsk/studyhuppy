package com.studyhub.kartei.adapter.web.controller;

import com.studyhub.kartei.adapter.web.CookieHandler;
import com.studyhub.kartei.adapter.web.controller.KarteikarteController;
import com.studyhub.kartei.adapter.web.controller.SetFragetypRequest;
import com.studyhub.kartei.domain.model.FrageTyp;
import com.studyhub.kartei.domain.model.Stapel;
import com.studyhub.kartei.service.application.StapelService;
import com.studyhub.kartei.util.StapelMother;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(KarteikarteController.class)
public class KarteikarteControllerTest {

	@Autowired
	MockMvc mvc;

	@MockitoBean
	StapelService stapelService;

	@Test
	@DisplayName("/lernen/{karteiSetId}/new-karte ist mit beliebiger Id erreichbar (Model wird korrekt mit Cookie-Daten befüllt udn gibt gewünschte View zurück)")
	void test_01() throws Exception {
		Stapel stapel = StapelMother.initSet();
		HttpServletRequest request = mock(HttpServletRequest.class);
		MockedStatic<CookieHandler> mockedStatic = mockStatic(CookieHandler.class);
		mockedStatic.when(() -> CookieHandler.frageTypCookieExists(request)).thenReturn(false);
		mockedStatic.when(() -> CookieHandler.createFrageTypCookie(FrageTyp.NORMAL)).thenReturn(new Cookie("frageTyp", "NORMAL"));
		mockedStatic.when(() -> CookieHandler.anzahlAntwortenCookieExists(request)).thenReturn(false);
		mockedStatic.when(() -> CookieHandler.createAnzahlAntwortenCookie(2)).thenReturn(new Cookie("anzahlAntworten", "2"));
		when(stapelService.findByFachId(stapel.getFachId().toString())).thenReturn(stapel);

		mvc.perform(get("/lernen/{karteiSetId}/new-karte", stapel.getFachId().toString()))
				.andExpect(status().isOk())
				.andExpect(view().name("/new-karte/new-karte"))
				.andExpect(model().attribute("fragetypen", FrageTyp.allTypes()))
				.andExpect(model().attribute("fragetypChoice", FrageTyp.NORMAL.toString()))
				.andExpect(model().attribute("karteiSetId", stapel.getFachId().toString()))
				.andExpect(model().attribute("stapelName", stapel.getName()))
				.andExpect(model().attribute("anzahlAntworten", 2));
	}
	//INFO: der test schlägt mit ./gradlew immer fehl, manuell aber grün
	@Test
	@DisplayName("/set-fragetyp ist erreichbar und updated das Cookie anhand des FrageTyps im Request " +
			"und führt einen Redirect durch")
	void test_02() throws Exception {
		MockedStatic<CookieHandler> mockedStatic = mockStatic(CookieHandler.class);
		String karteiSetId = UUID.randomUUID().toString();
		String jsonRequest = """
        {
            "frageTyp": "NORMAL",
            "karteiSetId": "%s"
        }
        """.formatted(karteiSetId);

		mvc.perform(post("/set-fragetyp")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonRequest))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/lernen/" + karteiSetId + "/new-karte"));

		mockedStatic.verify(() ->
				CookieHandler.updateFrageTypCookie(any(SetFragetypRequest.class), any(HttpServletResponse.class)),
				times(1));
	}

	//INFO: der test schlägt mit ./gradlew immer fehl, manuell aber grün
	@Test
	@DisplayName("/set-fragetyp ist bei invaliden Requests nicht erreichbar und gibt die error-view zurück")
	void test_03() throws Exception {
		MockedStatic<CookieHandler> mockedStatic = mockStatic(CookieHandler.class);
		mockedStatic.when(() ->
				CookieHandler.updateFrageTypCookie(any(SetFragetypRequest.class), any(HttpServletResponse.class))
		).thenAnswer(invocation -> null);

		String jsonRequest = """
			{
			    "frageTyp": "NO",
			    "karteiSetId": "someuuid"
			}
			""";

		mvc.perform(post("/set-fragetyp")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonRequest))
				.andExpect(status().isNotAcceptable())
				.andExpect(view().name("error"));

		mockedStatic.verify(() ->
						CookieHandler.updateFrageTypCookie(any(SetFragetypRequest.class), any(HttpServletResponse.class)),
				times(0));
	}

	@Test
	@DisplayName("Post-Request auf /create-karteikarte-normal/{karteiSetId} mit gültigen Parametern ist erfolgreich und redirected erfolgreich")
	void test_04() throws Exception {
		String karteiSetId = UUID.randomUUID().toString();
		mvc.perform(post("/create-karteikarte-normal/{karteiSetId}", karteiSetId)
						.param("frage", "frage1")
						.param("antwort", "antwort")
						.param("notiz", "notiz1")
						.param("frageTyp", FrageTyp.NORMAL.toString()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/lernen/%s/new-karte".formatted(karteiSetId)));
	}

	@Test
	@DisplayName("Post-Request auf /create-karteikarte-normal ohne Frage ist nicht möglich und meldet einen Fehler im Formular")
	void test_05() throws Exception {
		String karteiSetId = UUID.randomUUID().toString();
		mvc.perform(post("/create-karteikarte-normal/{karteiSetId}", karteiSetId)
						.param("frage", "")
						.param("antwort", "antwort")
						.param("notiz", "notiz1")
						.param("frageTyp", FrageTyp.NORMAL.toString()))
				.andExpect(status().isOk())
				.andExpect(model().attributeErrorCount("newKarteikarteForm", 1))
				.andExpect(view().name("/new-karte/new-karte"));
	}

	@Test
	@DisplayName("Post-Request auf /create-karteikarte-normal/{karteiSetId} ohne Antwort ist nicht möglich und meldet einen Fehler im Formular")
	void test_06() throws Exception {
		String karteiSetId = UUID.randomUUID().toString();
		mvc.perform(post("/create-karteikarte-normal/{karteiSetId}", karteiSetId)
						.param("frage", "frage1")
						.param("antwort", "")
						.param("notiz", "notiz1")
						.param("frageTyp", FrageTyp.NORMAL.toString()))
				.andExpect(status().isOk())
				.andExpect(model().attributeErrorCount("newKarteikarteForm", 1))
				.andExpect(view().name("/new-karte/new-karte"));
	}

	@Test
	@DisplayName("Post-Request auf /create-karteikarte/{karteiSetId} ohne Antwort und ohne Frage ist nicht möglich und meldet zwei Fehler im Formular")
	void test_07() throws Exception {
		String karteiSetId = UUID.randomUUID().toString();
		mvc.perform(post("/create-karteikarte-normal/{karteiSetId}", karteiSetId)
						.param("frage", "")
						.param("antwort", "")
						.param("notiz", "notiz1")
						.param("frageTyp", FrageTyp.NORMAL.toString()))
				.andExpect(status().isOk())
				.andExpect(model().attributeErrorCount("newKarteikarteForm", 2))
				.andExpect(view().name("/new-karte/new-karte"));
	}

	@Test
	@DisplayName("Post-Request auf /create-karteikarte/{karteiSetId} ohne Notiz ist möglich und redirected")
	void test_08() throws Exception {
		String karteiSetId = UUID.randomUUID().toString();
		mvc.perform(post("/create-karteikarte-normal/{karteiSetId}", karteiSetId)
						.param("frage", "f")
						.param("antwort", "a")
						.param("notiz", "")
						.param("frageTyp", FrageTyp.NORMAL.toString()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/lernen/%s/new-karte".formatted(karteiSetId)));
	}

	@Test
	@DisplayName("Ein Reqeust auf /set-anzahlAntworten ist erfolgreich und redirected erfolgreich")
	void test_9() throws Exception {
		String stapelId = UUID.randomUUID().toString();
		mvc.perform(post("/set-anzahlAntworten")
						.param("karteiSetId", stapelId)
						.param("anzahlAntworten", "2"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/lernen/%s/new-karte".formatted(stapelId)));
	}

	@Test
	@DisplayName("Ein Request auf /lernen/{karteiSetId}/new-karte befüllt das Model mit den gesetzten Cookie-Attributen aus dem Request")
	void test_10() throws Exception {
		Stapel stapel = StapelMother.initSet();

		when(stapelService.findByFachId(stapel.getFachId().toString())).thenReturn(stapel);

		mvc.perform(get("/lernen/{karteiSetId}/new-karte", stapel.getFachId().toString())
						.cookie(new Cookie("frageTyp", "NORMAL"), new Cookie("anzahlAntworten", "2")))
				.andExpect(status().isOk())
				.andExpect(view().name("/new-karte/new-karte"))
				.andExpect(model().attribute("fragetypen", FrageTyp.allTypes()))
				.andExpect(model().attribute("fragetypChoice", "NORMAL"))
				.andExpect(model().attribute("karteiSetId", stapel.getFachId().toString()))
				.andExpect(model().attribute("stapelName", stapel.getName()))
				.andExpect(model().attribute("anzahlAntworten", 2));
	}
}