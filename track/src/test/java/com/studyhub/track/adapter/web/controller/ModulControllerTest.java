package com.studyhub.track.adapter.web.controller;

import com.studyhub.track.adapter.kartei.CreateNewStapelRequest;
import com.studyhub.track.adapter.kartei.StapelRequestService;
import com.studyhub.track.adapter.web.controller.api.ModulApiController;
import com.studyhub.track.application.service.ModulService;
import com.studyhub.track.domain.model.modul.Modul;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.LinkedList;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Disabled
@WebMvcTest(ModulApiController.class)
public class ModulControllerTest {

	@Autowired
	MockMvc mvc;

	@MockitoBean
	ModulService modulService;

	@MockitoBean
	StapelRequestService stapelRequestService;


	@Test
	@DisplayName("Die Startseite '/dashboard' ist für nicht eingeloggte User nicht erreichbar")
	void test_02() throws Exception {
		mvc.perform(get("/dashboard"))
						.andExpect(status().is3xxRedirection());
	}

	@Test
	@DisplayName("'/bearbeiten' ist für eingeloggte User erreichbar")
	void test_03() throws Exception {
		mvc.perform(get("/bearbeiten"))
						.andExpect(status().isOk())
						.andExpect(view().name("configure"))
						.andExpect(model().attribute("modulList", new LinkedList<>()));
	}

	@Test
	@DisplayName("'/bearbeiten' ist für nicht eingeloggte User nicht erreichbar")
	void test_04() throws Exception {
		mvc.perform(get("/bearbeiten"))
					.andExpect(status().is3xxRedirection());
	}

	@Test
	@DisplayName("Ein Request auf /new-modul ist erfolgreich und es erfolgt ein redirect")
	void test_05() throws Exception {
		mvc.perform(post("/new-modul")
						.param("name", "mo1")
						.param("creditPoints", "10")
						.param("stapelCheckbox", "false")
						.with(csrf()))
						.andExpect(status().is3xxRedirection())
						.andExpect(redirectedUrl("/dashboard"));
	}

	@Test
	@DisplayName("Ein Request auf /new-modul ist für nicht eingeloggte USer nicht nicht möglich und es erfolgt ein redirect")
	void test_06() throws Exception {
		mvc.perform(post("/new-modul")
						.param("name", "mo1")
						.param("creditPoints", "10")
						.with(csrf()))
						.andExpect(status().is3xxRedirection());
	}

	@Test
	@DisplayName("Ein Request auf /new-modul erstellt mit übermittelten Daten ein neues Modul, ohne einen Stapel zu erstellen")
	void test_07() throws Exception {
		mvc.perform(post("/new-modul")
						.param("name", "mo1")
						.param("creditPoints", "10")
						.param("stapelCheckbox", "false")
						.with(csrf()))
						.andExpect(status().is3xxRedirection());
		verify(modulService).saveNewModul(any(Modul.class));
	}

	@Test
	@DisplayName("Ein Request auf /reset ist für nicht eingeloggte User nicht möglich und redirect")
	void test_08() throws Exception {
		mvc.perform(post("/reset")
						.param("fachId", UUID.randomUUID().toString())
						.with(csrf()))
						.andExpect(status().is3xxRedirection());
	}

	@Test
	@DisplayName("Ein Request auf /reset reseted die Sekunden eines Moduls")
	void test_09() throws Exception {
		mvc.perform(post("/reset")
						.param("fachId", UUID.randomUUID().toString())
						.with(csrf()));
		verify(modulService).resetModulTime(any(UUID.class));
	}

	@Test
	@DisplayName("Ein Request auf /delete ist erfolgreich und redirect")
	void test_10() throws Exception {
		mvc.perform(post("/delete")
						.param("fachId", UUID.randomUUID().toString())
						.with(csrf()))
						.andExpect(status().is3xxRedirection())
						.andExpect(redirectedUrl("/dashboard"));
	}

	@Test
	@DisplayName("Ein Request auf /delete löscht ein Modul anhand seiner FachId")
	void test_11() throws Exception {
		mvc.perform(post("/delete")
						.param("fachId", UUID.randomUUID().toString())
						.with(csrf()));
		verify(modulService).deleteModul(any(UUID.class));
	}

	@Test
	@DisplayName("Ein Request auf /add-time ist erfolgreich und redirected")
	void test_12() throws Exception {
		mvc.perform(post("/add-time")
						.param("fachId", UUID.randomUUID().toString())
						.with(csrf()))
						.andExpect(status().is3xxRedirection())
						.andExpect(redirectedUrl("/dashboard"));
	}

	@Test
	@DisplayName("Ein Request auf /add-time fügt die gesendete Zeit einem Modul anhand der FachId hinzu")
	void test_13() throws Exception {
		mvc.perform(post("/add-time")
						.param("fachId", UUID.randomUUID().toString())
						.param("time", "10:30")
						.with(csrf()));
		verify(modulService).addTime(any(UUID.class), anyString());
	}

	@Test
	@DisplayName("Ein Request auf /activate oder /deactivate ist erfolgreich, redirected und setzt den korrekten Status")
	void test_14() throws Exception {
		mvc.perform(post("/activate")
						.param("fachId", UUID.randomUUID().toString())
						.with(csrf()))
						.andExpect(status().is3xxRedirection())
						.andExpect(redirectedUrl("/dashboard"));

		mvc.perform(post("/deactivate")
						.param("fachId", UUID.randomUUID().toString())
						.with(csrf()))
						.andExpect(status().is3xxRedirection())
						.andExpect(redirectedUrl("/dashboard"));

		verify(modulService).changeActivity(any(UUID.class));
		verify(modulService).deactivateModul(any(UUID.class));
	}

	@Test
	@DisplayName("Ein Post-Request ohne Modulnamen auf /new-modul gibt einen Fehler zurück")
	void test_15() throws Exception {
		mvc.perform(post("/new-modul")
						.param("name", "")
						.with(csrf()))
						.andExpect(model().attributeErrorCount("modulForm", 1));
	}

	@Test
	@DisplayName("Ein Post-Request mit negativen Creditpunkten auf /new-modul gibt einen Fehler zurück")
	void test_16() throws Exception {
		mvc.perform(post("/new-modul")
						.param("name", "mod")
						.param("creditPoints", "-1")
						.with(csrf()))
						.andExpect(model().attributeErrorCount("modulForm", 1));
	}

	@Test
	@DisplayName("Ein Post-Request mit mehr als 30 Creditpunkten auf /new-modul gibt einen Fehler zurück")
	void test_17() throws Exception {
		mvc.perform(post("/new-modul")
						.param("name", "mod")
						.param("creditPoints", "31")
						.with(csrf()))
						.andExpect(model().attributeErrorCount("modulForm", 1));
	}

	@Test
	@DisplayName("Ein Request auf /new-modul erstellt mit übermittelten Daten ein neues Modul" +
			"und erstellt einen neuen Stapel, wenn die Stapel-Checkbox angekreuzt wurde UND die Request-Daten an das Kartei-System valid sind")
	void test_18() throws Exception {
		mvc.perform(post("/new-modul")
						.param("name", "mo1")
						.param("creditPoints", "10")
						.param("stapelCheckbox", "true")
						.param("stapelName", "set")
						.param("beschreibung", "cool")
						.param("lernstufen", "5m,2h,2d")
						.with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/dashboard"));
		verify(modulService).saveNewModul(any(Modul.class));
		verify(stapelRequestService).sendCreateNewStapelRequest(any(CreateNewStapelRequest.class));
	}

	@Test
	@DisplayName("Ein Request auf /new-modul erstellt mit übermittelten Daten kein neues Modul" +
			"und erstellt keinen neuen Stapel, wenn die Stapel-Checkbox angekreuzt wurde UND der setname invalid war")
	void test_19() throws Exception {
		//TODO: praktisch klappt es, implement test
	}

	@Test
	@DisplayName("Ein Request auf /new-modul erstellt mit übermittelten Daten kein neues Modul" +
			"und erstellt keinen neuen Stapel, wenn die Stapel-Checkbox angekreuzt wurde UND die Lernstufen invalid waren")
	void test_20() throws Exception {
		//TODO: praktisch klappt es, implement test
	}
}


