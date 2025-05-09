package com.studyhub.kartei.adapter.web.controller;

import com.studyhub.jwt.JWTService;
import com.studyhub.kartei.adapter.modul.ModulRequestService;
import com.studyhub.kartei.adapter.web.form.StapelForm;
import com.studyhub.kartei.domain.model.Stapel;
import com.studyhub.kartei.service.application.KarteikarteService;
import com.studyhub.kartei.service.application.StapelService;
import com.studyhub.kartei.service.application.lernzeit.LernzeitService;
import com.studyhub.kartei.util.JsoupUtil;
import com.studyhub.kartei.util.StapelMother;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
public class StapelControllerTest {

	@Autowired
	MockMvc mvc;

	@MockitoBean
	StapelService service;

	@MockitoBean
	ModulRequestService modulRequestService;

	@MockitoBean
	LernzeitService lernzeitService;

	@MockitoBean
	JWTService jwtService;

	@MockitoBean
	KarteikarteService karteikarteService;

	@Test
	@DisplayName("/kartei-uebersicht ist erreichbar und gibt das korrekte Template zurück wird angezeigt")
	@WithMockUser
	void test_01() throws Exception {
		mvc.perform(get("/kartei-uebersicht")
						.cookie(new Cookie("auth_token", "token")))
			.andExpect(status().isOk())
			.andExpect(view().name("kartei-dashboard"));
	}

	@Test
	@DisplayName("Model von /kartei-uebersicht enthält die korrekten Attribute")
	@WithMockUser
	void test_02() throws Exception {
		mvc.perform(get("/kartei-uebersicht")
				.cookie(new Cookie("auth_token", "token")))
				.andExpect(model().attribute("karteisetList", new LinkedList<>()));
	}

	@Test
	@DisplayName("Route /new-set ist erreichbar und gibt die korrekte View wieder")
	@WithMockUser
	void test_03() throws Exception {
		mvc.perform(get("/new-set")
						.cookie(new Cookie("auth_token", "token")))
				.andExpect(status().isOk())
				.andExpect(view().name("new-set"));
	}

	@Test
	@DisplayName("POST-Request auf /new-set ist erfolgreich und redirected zur Startseite")
	@WithMockUser
	void test_04() throws Exception {
		mvc.perform(post("/new-set")
						.param("setName", "setname")
						.param("beschreibung", "beschreibung")
						.param("modul-link", "")
						.param("lernIntervalle", "59m,23h,31d")
						.cookie(new Cookie("auth_token", "token")))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/kartei-uebersicht"));
	}

	@Test
	@DisplayName("POST-Request auf /new-set ohne einen Setnamen ist nicht möglich und leitet /new-set, wo ein Fehler angezeigt wird")
	@WithMockUser
	void test_05() throws Exception {
		mvc.perform(post("/new-set")
						.param("setName", "")
						.cookie(new Cookie("auth_token", "token")))
				.andExpect(model().attributeErrorCount("stapelForm", 1))
				.andExpect(status().isNotAcceptable())
				.andExpect(view().name("new-set"));
	}

	@Disabled("ist glaube ich nicht mehr nötig, muss ich später noch gucken")
	@Test
	@DisplayName("POST-Request auf /new-set mit allen Input-Values befüllt das KarteikarteSetForm-Objekt korrekt")
	@WithMockUser
	void test_06() throws Exception {
		mvc.perform(post("/new-set")
						.param("setName", "Mein Test Set")
						.param("beschreibung", "Dies ist eine Beschreibung")
						.param("modulFachId", "6f8a3d6e-2c4b-4f8e-924b-8e5f9a6d3b1c")
						.param("lernIntervalle", "59m,23h,31d")
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.cookie(new Cookie("auth_token", "token")))
						.andExpect(flash().attribute("karteikarteSetForm",
							new StapelForm("Mein Test Set", "Dies ist eine Beschreibung", "6f8a3d6e-2c4b-4f8e-924b-8e5f9a6d3b1c","59m,23h,31d")));
	}

	@Test
	@DisplayName("POST-Request auf /new-set mit inkorrekten Intervall-Pattern ist nicht möglich und leitet /new-set, wo ein Fehler angezeigt wird")
	@WithMockUser
	void test_07() throws Exception {
		String PATTERN_1 = "10d10m";
		String PATTERN_2 = "60m,1h";
		String PATTERN_3 = "30m,24h";
		String PATTERN_4 = "59m,23h,32d";

		mvc.perform(post("/new-set")
						.param("setName", "setname")
						.param("beschreibung", "Dies ist eine Beschreibung")
						.param("modulFachId", "6f8a3d6e-2c4b-4f8e-924b-8e5f9a6d3b1c")
						.param("lernIntervalle", PATTERN_1)
						.cookie(new Cookie("auth_token", "token")))
				.andExpect(model().attributeErrorCount("stapelForm", 1))
				.andExpect(status().isNotAcceptable())
				.andExpect(view().name("new-set"));

		mvc.perform(post("/new-set")
						.param("setName", "setname")
						.param("beschreibung", "Dies ist eine Beschreibung")
						.param("modulFachId", "6f8a3d6e-2c4b-4f8e-924b-8e5f9a6d3b1c")
						.param("lernIntervalle", PATTERN_2)
						.cookie(new Cookie("auth_token", "token")))
				.andExpect(model().attributeErrorCount("stapelForm", 1))
				.andExpect(status().isNotAcceptable())
				.andExpect(view().name("new-set"));

		mvc.perform(post("/new-set")
						.param("setName", "setname")
						.param("beschreibung", "Dies ist eine Beschreibung")
						.param("modulFachId", "6f8a3d6e-2c4b-4f8e-924b-8e5f9a6d3b1c")
						.param("lernIntervalle", PATTERN_3)
						.cookie(new Cookie("auth_token", "token")))
				.andExpect(model().attributeErrorCount("stapelForm", 1))
				.andExpect(status().isNotAcceptable())
				.andExpect(view().name("new-set"));

		mvc.perform(post("/new-set")
						.param("setName", "setname")
						.param("beschreibung", "Dies ist eine Beschreibung")
						.param("modulFachId", "6f8a3d6e-2c4b-4f8e-924b-8e5f9a6d3b1c")
						.param("lernIntervalle", PATTERN_4)
						.cookie(new Cookie("auth_token", "token")))
				.andExpect(model().attributeErrorCount("stapelForm", 1))
				.andExpect(status().isNotAcceptable())
				.andExpect(view().name("new-set"));
	}

	@Test
	@DisplayName("Aufruf von /kartei-uebersicht setzt einen Cookie 'sessionKarteIndex' mit value=0")
	@WithMockUser
	void test_8() throws Exception {
		mvc.perform(get("/kartei-uebersicht")
						.cookie(new Cookie("auth_token", "token")))
				.andExpect(status().isOk())
				.andExpect(view().name("kartei-dashboard"))
				.andExpect(cookie().exists("sessionKarteIndex"))
				.andExpect(cookie().value("sessionKarteIndex", "0"));
	}

	@Test
	@DisplayName("Wenn es fällige Karteikarten im Stapel gibt, wird die vorraussichtliche Lernzeit korrekt angezeigt.")
	@WithMockUser
	void test_9() throws Exception {
		LocalDateTime today = LocalDateTime.of(2025, 1, 10, 10, 0);
		List<Stapel> userStapel = StapelMother.initTwoStapel();

		when(service.areKarteiSetsAvailable()).thenReturn(true);
		when(service.findByUsername("user1")).thenReturn(userStapel);
		when(jwtService.extractUsername(anyString())).thenReturn("user1");
		when(lernzeitService.dateOfToday()).thenReturn(today);
		when(lernzeitService.getVorraussichtlicheLernzeitFürStapel(userStapel.get(0).getFachId(), today)).thenReturn(500);
		when(lernzeitService.calculateZeitAsString(500)).thenReturn("~8m");
		when(lernzeitService.showVorraussichtlicheLernzeitWhen(userStapel.get(0).getFachId(), today)).thenReturn(true);

		String htmlString = mvc.perform(get("/kartei-uebersicht")
						.cookie(new Cookie("auth_token", "token")))
				.andExpect(model().attribute("today", today))
				.andExpect(model().attribute("lernzeitService", lernzeitService))
				.andExpect(model().attribute("karteiSetsAvailable", true))
				.andExpect(model().attribute("karteisetList", userStapel))
				.andExpect(model().attribute("modulService", modulRequestService))
				.andReturn()
				.getResponse()
				.getContentAsString();

		assertThat(JsoupUtil.getTextFromElementById(htmlString, "vorrausichtliche-zeit-label")).isEqualTo("(~8m)");
	}
}
