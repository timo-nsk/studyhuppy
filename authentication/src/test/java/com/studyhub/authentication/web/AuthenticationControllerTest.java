package com.studyhub.authentication.web;

import com.studyhub.jwt.JWTService;
import jakarta.servlet.http.HttpServletResponse;
import com.studyhub.authentication.config.JwtAuthFilter;
import com.studyhub.authentication.service.AppUserDetailsService;
import com.studyhub.authentication.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@WebAppConfiguration
public class AuthenticationControllerTest {
	@Autowired WebApplicationContext context;
	@MockitoBean AuthenticationService authenticationService;
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
	@DisplayName("/login ist für alle Besucher erreichbar")
	void test_1() throws Exception {
		mvc.perform(get("/login"))
				.andExpect(status().isOk())
				.andExpect(view().name("login"));
	}

	@Test
	@DisplayName("/login/auth ist mit validen LoginRequest-Daten erreichbar und verifiziert die Daten")
	void test_2() throws Exception {
		LoginRequest requestStub = new LoginRequest("user1", "12345678");

		mvc.perform(post("/login/auth")
						.param("username", requestStub.username())
						.param("password", requestStub.password()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/home"));

		verify(authenticationService).verify(requestStub);
	}

	@Test
	@DisplayName("Wenn kein username in das Login-Formular eingegeben wird, wird auf /login geleitet und ein Fehler angezeigt")
	void test_3() throws Exception {
		LoginRequest requestStub = new LoginRequest("", "12345678");

		mvc.perform(post("/login/auth")
						.param("username", requestStub.username())
						.param("password", requestStub.password()))
				.andExpect(status().isOk())
				.andExpect(view().name("login"))
				.andExpect(model().attributeErrorCount("loginRequest", 1));
	}

	@Test
	@DisplayName("Wenn kein password in das Login-Formular eingegeben wird, wird auf /login geleitet und ein Fehler angezeigt")
	void test_4() throws Exception {
		LoginRequest requestStub = new LoginRequest("user1", "");

		mvc.perform(post("/login/auth")
						.param("username", requestStub.username())
						.param("password", requestStub.password()))
				.andExpect(status().isOk())
				.andExpect(view().name("login"))
				.andExpect(model().attributeErrorCount("loginRequest", 1));
	}

	@Test
	@DisplayName("Wenn kein password UND kein username in das Login-Formular eingegeben werden, wird auf /login geleitet und zwei Fehler angezeigt")
	void test_5() throws Exception {
		LoginRequest requestStub = new LoginRequest("", "");

		mvc.perform(post("/login/auth")
						.param("username", requestStub.username())
						.param("password", requestStub.password()))
				.andExpect(status().isOk())
				.andExpect(view().name("login"))
				.andExpect(model().attributeErrorCount("loginRequest", 2));
	}

	@Test
	@DisplayName("Wenn kein password UND kein username in das Login-Formular eingegeben werden, wird auf /login geleitet und zwei Fehler angezeigt")
	void test_6() throws Exception {
		LoginRequest requestStub = new LoginRequest("", "");

		mvc.perform(post("/login/auth")
						.param("username", requestStub.username())
						.param("password", requestStub.password()))
				.andExpect(status().isOk())
				.andExpect(view().name("login"))
				.andExpect(model().attributeErrorCount("loginRequest", 2));
	}

	@Test
	@DisplayName("/login/auth ist mit validen LoginRequest-Daten erreichbar und leitet auf die Seite zurück, die als Wert im redirect-Feld steht")
	void test_7() throws Exception {
		LoginRequest requestStub = new LoginRequest("user1", "12345678");
		String redirect = "http://localhost:8081/kartei-uebersicht";

		mvc.perform(post("/login/auth")
						.param("username", requestStub.username())
						.param("password", requestStub.password())
						.param("redirect", redirect))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl(redirect));

		verify(authenticationService).verify(requestStub);
	}

	@Test
	@DisplayName("/login/auth ist mit validen LoginRequest-Daten erreichbar und würde einen Cookie aus dem generierten Token in die HttpSerlvetResponse schreiben")
	void test_8() throws Exception {
		LoginRequest requestStub = new LoginRequest("user1", "12345678");
		when(authenticationService.verify(requestStub)).thenReturn(anyString());

		mvc.perform(post("/login/auth")
						.param("username", requestStub.username())
						.param("password", requestStub.password()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/home"));

		verify(authenticationService).createAuthTokenCookie(any(HttpServletResponse.class), anyString());
	}

	@Test
	@DisplayName("/login mit einem Query-Parameter 'redirect' ist erfolgreich und der Wert wird ins Model geschrieben")
	void test_9() throws Exception {
		mvc.perform(get("/login")
						.queryParam("redirect", "http://localhost:8081/kartei-uebersicht"))
				.andExpect(status().isOk())
				.andExpect(view().name("login"))
				.andExpect(model().attribute("redirect", "http://localhost:8081/kartei-uebersicht"));
	}

	@Test
	@DisplayName("/login/auth ist mit validen LoginRequest-Daten erreichbar und leerem redirect-value redirected auf /home")
	void test_10() throws Exception {
		LoginRequest requestStub = new LoginRequest("user1", "12345678");
		when(authenticationService.verify(requestStub)).thenReturn(anyString());

		mvc.perform(post("/login/auth")
						.param("username", requestStub.username())
						.param("password", requestStub.password())
						.param("redirect", ""))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/home"));
	}

	@Test
	@DisplayName("Wenn der User oder Password falsch sind, wird ein Fehler auf der Seite angezeigt")
	void test_11() throws Exception {
		LoginRequest requestStub = new LoginRequest("doesnotexist", "iswrong");
		when(authenticationService.verify(requestStub)).thenThrow(new BadCredentialsException("exception"));

		mvc.perform(post("/login/auth")
						.param("username", requestStub.username())
						.param("password", requestStub.password()))
				.andExpect(status().isBadRequest())
				.andExpect(view().name("login"));

		//TODO: noch mit jsoup prüfen, ob die Fehlermeldung auf der seite ist
	}

	@Test
	@DisplayName("Wennd er auth_token falsch ist, wird auf die Error-Seite verwiesen und dort der Fehler angezegt ODER wieder auf die Login seite")
	void test_12() {
		// TODO: implement test case
	}
}