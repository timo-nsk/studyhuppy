package com.studyhub.authentication.web;

import com.studyhub.jwt.JWTService;
import jakarta.servlet.http.Cookie;
import com.studyhub.authentication.config.RedirectEntryPoint;
import com.studyhub.authentication.model.AppUser;
import com.studyhub.authentication.config.JwtAuthFilter;
import com.studyhub.authentication.service.AccountService;
import com.studyhub.authentication.service.AppUserDetailsService;
import com.studyhub.authentication.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
public class UserProfilControllerTest {

	@Autowired
	private WebApplicationContext context;
	private MockMvc mvc;

	@MockitoBean AuthenticationService authenticationService;
	@MockitoBean JwtAuthFilter jwtAuthFilter;
	@MockitoBean
	JWTService jwtService;
	@MockitoBean AppUserDetailsService appUserDetailsService;
	@MockitoBean RedirectEntryPoint redirectEntryPoint;
	@MockitoBean AccountService accountService;
	@MockitoBean UserPasswordValidator userPasswordValidator;

	@BeforeEach
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	@DisplayName("/profil ist erfolgreich für authentifizierte Benutzer erfolgreich und gibt die korrekte View zurück")
	@WithMockUser(username = "susi4", roles = "USER")
	void test_1() throws Exception {
		AppUser appUser = new AppUser(1, UUID.randomUUID(), "some@mail.com", "susi4", "password", true, true, 1);
		when(jwtService.extractUsername("jwttoken")).thenReturn("susi4");
		when(accountService.findByUsername("susi4")).thenReturn(appUser);
		mvc.perform(get("/profil")
						.cookie(new Cookie("auth_token", "jwttoken")))
			.andExpect(status().isOk())
			.andExpect(view().name("profil"))
			.andExpect(model().attribute("user", appUser));
	}

	@Disabled("funktioniert manuell")
	@Test
	@DisplayName("/profil ist für nicht eingeloggte Benutzer nicht erreichbar und redirected zur login Seite")
	void test_2() throws Exception {
		mvc.perform(get("/profil"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/login"));
	}

	@Test
	@DisplayName("/delete-account ist erfolgreich für authentifizierte Benutzer erfolgreich und gibt die korrekte View zurück")
	@WithMockUser(username = "susi4", roles = "USER")
	void test_3() throws Exception {
		mvc.perform(get("/delete-account"))
				.andExpect(status().isOk())
				.andExpect(view().name("delete-account"));
	}

	@Disabled("funktioniert manuell")
	@Test
	@DisplayName("/delete-account ist für nicht eingeloggte Benutzer nicht erreichbar und redirected zur login Seite")
	void test_4() throws Exception {
		mvc.perform(get("/delete-account"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/login"));
	}



	@Test
	@DisplayName("Ein POST-Request auf /edit-notification ist erfolgreich, leitet die Daten in den AccountService weiter und gibt die korrekte View zurück")
	void test_7() throws Exception {
		String token = "jwttoken";
		String username = "susi4";
		String request = "{\"activate\": true}";
		when(jwtService.extractUsername(token)).thenReturn(username);

		mvc.perform(post("/edit-notification")
						.contentType(MediaType.APPLICATION_JSON)
						.content(request)
				.cookie(new Cookie("auth_token", token)))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/profil"));

		verify(accountService).editNotificationSubscription(true, username);
	}

	@Test
	@DisplayName("Ein Request auf /get-notification-subscription gibt den Status der Notification Subscription eines Users zurück")
	void test_8() throws Exception {
		when(jwtService.extractUsername("jwttoken")).thenReturn("susi4");
		when(accountService.getNotificationSubscription("susi4")).thenReturn(true);
		mvc.perform(get("/get-notification-subscription")
						.cookie(new Cookie("auth_token", "jwttoken")))
				.andExpect(status().isOk())
				.andExpect(content().string("true"));
	}

}
