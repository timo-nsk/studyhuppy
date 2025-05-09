package com.studyhub.authentication.web;

import com.studyhub.authentication.model.AppUser;
import com.studyhub.authentication.config.JwtAuthFilter;
import com.studyhub.authentication.service.AppUserDetailsService;
import com.studyhub.authentication.service.RegistrationService;
import com.studyhub.jwt.JWTService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@WebAppConfiguration
public class RegistrationControllerTest {

	@Autowired WebApplicationContext context;
	@MockitoBean RegistrationService registrationService;
	@MockitoBean JwtAuthFilter jwtAuthFilter;
	@MockitoBean
	JWTService jwtService;
	@MockitoBean AppUserDetailsService appUserDetailsService;
	MockMvc mvc;

	@BeforeEach
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	@DisplayName("/register ist für alle Besucher erreichbar und gibt die korrekte View zurück")
	void test_1() throws Exception {
		mvc.perform(get("/register"))
				.andExpect(status().isOk())
				.andExpect(view().name("register"));
	}

	@Test
	@DisplayName("/register/new-user ist mit validen Daten erreichbar und registriert einen neuen Benutzer mit den gesendeteten Daten")
	void test_2() throws Exception {
		when(registrationService.register(any(AppUser.class))).thenReturn(true);

		mvc.perform(post("/register/new-user")
						.param("mail", "some@mail.com")
						.param("username", "user1")
						.param("password", "12345678")
						.param("notificationSubscription", "false")
						.param("acceptedAgb", "true"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/home"));
	}

	@Test
	@DisplayName("Wenn das Registrieren eines neuen Users nicht erfolgreich war, wird eine Exception im Controller geworfen")
	void test_3() throws Exception {
		when(registrationService.register(any(AppUser.class))).thenReturn(false);

		mvc.perform(post("/register/new-user")
						.param("mail", "some@mail.com")
						.param("username", "user1")
						.param("password", "12345678")
						.param("notificationSubscription", "false")
						.param("acceptedAgb", "true"))
				.andExpect(status().isInternalServerError())
				.andExpect(view().name("error"));
	}

	@Test
	@DisplayName("Wenn keine valider E-Mail-Adresse eingegeben wurde, wird wieder auf /register geleitet und ein Fehler angezeigt")
	void test_4() throws Exception {
		mvc.perform(post("/register/new-user")
						.param("mail", "somemail.com")
						.param("username", "user1")
						.param("password", "12345678")
						.param("notificationSubscription", "false")
						.param("acceptedAgb", "true"))
				.andExpect(status().isOk())
				.andExpect(model().attributeErrorCount("registerForm", 1));
	}

	@Test
	@DisplayName("Wenn kein Username eingegeben wurde, wird wieder auf /register geleitet und ein Fehler angezeigt")
	void test_5() throws Exception {
		mvc.perform(post("/register/new-user")
						.param("mail", "some@mail.com")
						.param("username", "")
						.param("password", "12345678")
						.param("notificationSubscription", "false")
						.param("acceptedAgb", "true"))
				.andExpect(status().isOk())
				.andExpect(model().attributeErrorCount("registerForm", 1));
	}

	@Test
	@DisplayName("Wenn kein Passwort eingegeben wurde, wird wieder auf /register geleitet und ein Fehler angezeigt")
	void test_6() throws Exception {
		mvc.perform(post("/register/new-user")
						.param("mail", "some@mail.com")
						.param("username", "user1")
						.param("password", "")
						.param("notificationSubscription", "false")
						.param("acceptedAgb", "true"))
				.andExpect(status().isOk())
				.andExpect(model().attributeErrorCount("registerForm", 2));
	}

	@Test
	@DisplayName("Wenn die AGB nicht akzeptiert wurden, wird wieder auf /register geleitet und ein Fehler angezeigt")
	void test_7() throws Exception {
		mvc.perform(post("/register/new-user")
						.param("mail", "some@mail.com")
						.param("username", "user1")
						.param("password", "12345678")
						.param("notificationSubscription", "false")
						.param("acceptedAgb", "null"))
				.andExpect(status().isOk())
				.andExpect(model().attributeErrorCount("registerForm", 1));
	}

	@Test
	@DisplayName("Wenn keine E-Mail-Adresse eingegeben wurde, wird wieder auf /register geleitet und vier Fehler angezeigt")
	void test_8() throws Exception {
		mvc.perform(post("/register/new-user")
						.param("mail", "")
						.param("username", "user1")
						.param("password", "12345678")
						.param("notificationSubscription", "false")
						.param("acceptedAgb", "true"))
				.andExpect(status().isOk())
				.andExpect(model().attributeErrorCount("registerForm", 1));
	}

	@Test
	@DisplayName("Wenn das Passwort keine 8 Zeichen lang ist, wird wieder auf /register geleitet und ein Fehler angezeigt")
	void test_9() throws Exception {
		mvc.perform(post("/register/new-user")
						.param("mail", "some@mail.com")
						.param("username", "user1")
						.param("password", "1234")
						.param("notificationSubscription", "false")
						.param("acceptedAgb", "true"))
				.andExpect(status().isOk())
				.andExpect(model().attributeErrorCount("registerForm", 1));
	}
}
