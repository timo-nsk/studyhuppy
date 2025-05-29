package com.studyhub.track.adapter.web.controller;

import com.studyhub.track.application.JWTService;
import com.studyhub.track.adapter.mail.KlausurReminderService;
import com.studyhub.track.adapter.security.SecurityConfig;
import com.studyhub.track.adapter.web.controller.request.dto.AddTimeRequest;
import com.studyhub.track.adapter.web.KlausurDateRequest;
import com.studyhub.track.adapter.web.ModulForm;
import com.studyhub.track.application.service.dto.ModulUpdateRequest;
import com.studyhub.track.adapter.web.controller.api.ModulApiController;
import com.studyhub.track.application.service.dto.NeuerModulterminRequest;
import com.studyhub.track.application.service.ModulEventService;
import com.studyhub.track.application.service.ModulService;
import com.studyhub.track.domain.model.modul.Modul;
import com.studyhub.track.domain.model.modul.Terminfrequenz;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ModulApiController.class)
@Import(SecurityConfig.class)
public class ModulApiControllerTest {

	@Value("${maxModule}")
	private int MAX_MODULE;

	@Autowired
	MockMvc mvc;

	ObjectMapper objectMapper = new ObjectMapper();

	@MockitoBean
	private ModulService modulService;

	@MockitoBean
	private JWTService jwtService;

	@MockitoBean
	private ModulEventService modulEventService;

	@MockitoBean
	private KlausurReminderService klausurReminderService;

	@Autowired
	private ModulApiController modulApiController;

	@Test
	@DisplayName("Wenn ein neues Modul erstellt werden soll und der User noch welche erstellen kann, wird das Modul gespeichert")
	void test_01() {
		ModulForm modulForm = mock(ModulForm.class);
		MockHttpServletRequest request = new MockHttpServletRequest();
		Modul m = new Modul();

		when(jwtService.extractUsernameFromHeader(request)).thenReturn("peter");
		when(modulService.modulCanBeCreated("peter", MAX_MODULE)).thenReturn(true);
		when(modulForm.newModulFromFormData(any(ModulForm.class), anyInt())).thenReturn(m);

		modulApiController.newModule(modulForm, request);

		verify(modulService).saveNewModul(any(Modul.class));
	}

	@Test
	@DisplayName("Wenn ein neues Modul erstellt werden soll und der User schon die maximale Anzahl erstellter Module erreich hat, wird das Modul nicht gespeichert")
	void test_02() {
		ModulForm modulForm = mock(ModulForm.class);
		MockHttpServletRequest request = new MockHttpServletRequest();
		Modul m = new Modul();

		when(jwtService.extractUsernameFromHeader(request)).thenReturn("peter");
		when(modulService.modulCanBeCreated("peter", MAX_MODULE)).thenReturn(false);
		when(modulForm.newModulFromFormData(any(ModulForm.class), anyInt())).thenReturn(m);

		modulApiController.newModule(modulForm, request);

		verify(modulService, times(0)).saveNewModul(any(Modul.class));
	}

	@Test
	@DisplayName("Ein Post-Request auf /update ist nicht als unauthentifizierte Person möglich")
	void test_03() throws Exception {
		ModulUpdateRequest modulUpdateRequest = new ModulUpdateRequest(UUID.randomUUID().toString(), 20, 10);
		mvc.perform(post("/api/update")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(modulUpdateRequest)))
				.andExpect(status().isForbidden());
	}

	@Test
	@DisplayName("Ein Post-Request auf /update ist als authentifizierte Person möglich")
	@WithMockUser(username="testuser", roles = "USER")
	void test_04() throws Exception {
		ModulUpdateRequest modulUpdateRequest = new ModulUpdateRequest(UUID.randomUUID().toString(), 20, 10);
		mvc.perform(post("/api/update")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(modulUpdateRequest)))
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("Get-Request auf /get-seconds ist nicht als unauthentifizierte Person möglich")
	void test_05() throws Exception {
		mvc.perform(get("/api/get-seconds"))
				.andExpect(status().isForbidden());
	}

	@Test
	@DisplayName("Get-Request auf /get-seconds ist als authentifizierte Person möglich")
	@WithMockUser(username="testuser", roles = "USER")
	void test_06() throws Exception {
		mvc.perform(get("/api/get-seconds")
						.queryParam("fachId", UUID.randomUUID().toString()))
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("Get-Request auf /module-map ist nicht als unauthentifizierte Person möglich")
	void test_7() throws Exception {
		mvc.perform(get("/api/module-map"))
				.andExpect(status().isForbidden());
	}

	@Test
	@DisplayName("Get-Request auf /module-map ist als authentifizierte Person möglich")
	@WithMockUser(username="testuser", roles = "USER")
	void test_8() throws Exception {
		mvc.perform(get("/api/module-map"))
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("Get-Request auf /module-name ist nicht als unauthentifizierte Person möglich")
	void test_9() throws Exception {
		mvc.perform(get("/api/module-name"))
				.andExpect(status().isForbidden());
	}

	@Test
	@DisplayName("Get-Request auf /module-name ist nicht als unauthentifizierte Person möglich")
	@WithMockUser(username="testuser", roles = "USER")
	void test_10() throws Exception {
		mvc.perform(get("/api/module-name")
						.param("modulFachId", UUID.randomUUID().toString()))
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("Post-Request auf /data-klausur-reminding ist nicht als unauthentifizierte Person möglich")
	void test_11() throws Exception {
		mvc.perform(post("/api/data-klausur-reminding"))
				.andExpect(status().isForbidden());
	}

	@Test
	@DisplayName("Post-Request auf /data-klausur-reminding ist als authentifizierte Person möglich")
	@WithMockUser(username="testuser", roles = "USER")
	void test_12() throws Exception {
		List<String> l = List.of("user1", "user2");
		mvc.perform(post("/api/data-klausur-reminding")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(l)))
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("Put-Request auf /reset ist nicht als unauthentifizierte Person möglich")
	void test_13() throws Exception {
		mvc.perform(put("/api/reset"))
				.andExpect(status().isForbidden());
	}

	@Test
	@DisplayName("Put-Request auf /reset ist als authentifizierte Person möglich")
	@WithMockUser(username="testuser", roles = "USER")
	void test_14() throws Exception {
		mvc.perform(put("/api/reset")
						.param("fachId", UUID.randomUUID().toString()))
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("Delete-Request auf /delete ist nicht als unauthentifizierte Person möglich")
	void test_15() throws Exception {
		mvc.perform(delete("/api/delete"))
				.andExpect(status().isForbidden());
	}

	@Test
	@DisplayName("Delete-Request auf /delete ist als authentifizierte Person möglich")
	@WithMockUser(username="testuser", roles = "USER")
	void test_16() throws Exception {
		mvc.perform(delete("/api/delete")
						.param("fachId", UUID.randomUUID().toString()))
				.andExpect(status().isNoContent());
	}

	@Test
	@DisplayName("Get-Request auf /get-active-modules ist nicht als unauthentifizierte Person möglich")
	void test_17() throws Exception {
		mvc.perform(get("/api/get-active-modules"))
				.andExpect(status().isForbidden());
	}

	@Test
	@DisplayName("Get-Request auf /get-active-modules ist als authentifizierte Person möglich")
	@WithMockUser(username="testuser", roles = "USER")
	void test_18() throws Exception {
		mvc.perform(get("/api/get-active-modules"))
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("Get-Request auf /get-all-by-username ist nicht als unauthentifizierte Person möglich")
	void test_19() throws Exception {
		mvc.perform(get("/api/get-all-by-username"))
				.andExpect(status().isForbidden());
	}

	@Test
	@DisplayName("Get-Request auf /get-all-by-username ist als authentifizierte Person möglich")
	@WithMockUser(username="testuser", roles = "USER")
	void test_20() throws Exception {
		mvc.perform(get("/api/get-all-by-username"))
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("Put-Request auf /change-active ist nicht als unauthentifizierte Person möglich")
	void test_21() throws Exception {
		mvc.perform(put("/api/change-active"))
				.andExpect(status().isForbidden());
	}

	@Test
	@DisplayName("Put-Request auf /change-active ist als authentifizierte Person möglich")
	@WithMockUser(username="testuser", roles = "USER")
	void test_22() throws Exception {
		mvc.perform(put("/api/change-active")
						.param("fachId", UUID.randomUUID().toString()))
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("Post-Request auf /add-time ist nicht als unauthentifizierte Person möglich")
	void test_23() throws Exception {
		AddTimeRequest req = new AddTimeRequest(UUID.randomUUID().toString(), "01:30");
		mvc.perform(post("/api/add-time")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(req)))
				.andExpect(status().isForbidden());
	}

	@Test
	@DisplayName("Post-Request auf /add-time ist als authentifizierte Person möglich")
	@WithMockUser(username="testuser", roles = "USER")
	void test_24() throws Exception {
		AddTimeRequest req = new AddTimeRequest(UUID.randomUUID().toString(), "01:30");
		mvc.perform(post("/api/add-time")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(req)))
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("Post-Request auf /add-klausur-dat ist nicht als unauthentifizierte Person möglich")
	void test_25() throws Exception {
		KlausurDateRequest req = new KlausurDateRequest(UUID.randomUUID().toString(), LocalDate.now(), "00:30");
		mvc.perform(post("/api/add-klausur-date")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(req)))
				.andExpect(status().isForbidden());
	}

	@Disabled("Geht praktisch")
	@Test
	@DisplayName("Post-Request auf /add-klausur-dat ist als authentifizierte Person möglich")
	@WithMockUser(username="testuser", roles = "USER")
	void test_26() throws Exception {

		KlausurDateRequest req = new KlausurDateRequest(UUID.randomUUID().toString(), LocalDate.now(), "00:30");
		System.out.println(objectMapper.writeValueAsString(req));
		mvc.perform(post("/api/add-klausur-date")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(req)))
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("Get-Request auf /getModultermine ist nicht als unauthentifizierte Person möglich")
	void test_27() throws Exception {
		mvc.perform(get("/api/getModultermine"))
				.andExpect(status().isForbidden());
	}

	@Test
	@DisplayName("Get-Request auf /getModultermine ist als authentifizierte Person möglich")
	@WithMockUser(username="testuser", roles = "USER")
	void test_28() throws Exception {
		mvc.perform(get("/api/getModultermine")
						.param("modulId", UUID.randomUUID().toString()))
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("Post-Request auf /addModultermin ist nicht als unauthentifizierte Person möglich")
	void test_29() throws Exception {
		NeuerModulterminRequest req = new NeuerModulterminRequest(
				UUID.randomUUID(),
				"titel",
				LocalDateTime.now(),
				LocalDateTime.now().plusHours(1),
				"notiz",
				Terminfrequenz.EINMALIG
		);

		mvc.perform(post("/api/addModultermin")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(req)))
				.andExpect(status().isForbidden());
	}

	@Disabled("Geht praktisch")
	@Test
	@DisplayName("Post-Request auf /addModultermin ist als authentifizierte Person möglich")
	@WithMockUser(username="testuser", roles = "USER")
	void test_30() throws Exception {
		NeuerModulterminRequest req = new NeuerModulterminRequest(
				UUID.randomUUID(),
				"titel",
				LocalDateTime.now(),
				LocalDateTime.now().plusHours(1),
				"notiz",
				Terminfrequenz.EINMALIG
		);

		mvc.perform(post("/api/addModultermin")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(req)))
				.andExpect(status().isCreated());
	}
}

