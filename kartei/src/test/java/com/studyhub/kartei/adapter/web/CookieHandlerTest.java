package com.studyhub.kartei.adapter.web;

import com.studyhub.kartei.adapter.web.controller.SetFragetypRequest;
import com.studyhub.kartei.domain.model.FrageTyp;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.studyhub.kartei.adapter.web.CookieHandler.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class CookieHandlerTest {

	@Test
	@DisplayName("Wenn ein Cookie in einer Request existiert, wird true zurückgegeben")
	void test_1() {
		HttpServletRequest req = mock(HttpServletRequest.class);
		Cookie[] cookies = {new Cookie("frageTyp", "NORMAL")};
		when(req.getCookies()).thenReturn(cookies);

		assertThat(frageTypCookieExists(req)).isEqualTo(true);
	}

	@Test
	@DisplayName("Wenn ein Cookie in einer Request nicht existiert, wird false zurückgegeben")
	void test_2() {
		HttpServletRequest req = mock(HttpServletRequest.class);
		Cookie[] cookies = {new Cookie("frageTypp", "NORMAL")};
		when(req.getCookies()).thenReturn(cookies);

		assertThat(frageTypCookieExists(req)).isEqualTo(false);
	}

	@Test
	@DisplayName("Wenn ein Cookie in einer Request für anzahlAntworten existiert, wird true zurückgegeben")
	void test_3() {
		HttpServletRequest req = mock(HttpServletRequest.class);
		Cookie[] cookies = {new Cookie("anzahlAntworten", "3")};
		when(req.getCookies()).thenReturn(cookies);

		assertThat(anzahlAntwortenCookieExists(req)).isTrue();
	}

	@Test
	@DisplayName("Wenn ein Cookie in einer Request für anzahlAntworten nicht existiert, wird false zurückgegeben")
	void test_4() {
		HttpServletRequest req = mock(HttpServletRequest.class);
		Cookie[] cookies = {new Cookie("anzahlAntwortenadrhrh", "3")};
		when(req.getCookies()).thenReturn(cookies);

		assertThat(anzahlAntwortenCookieExists(req)).isFalse();
	}

	@Test
	@DisplayName("Der Wert des Cookies für anzahlAnfragen kann bekommen werden")
	void test_5() {
		HttpServletRequest req = mock(HttpServletRequest.class);
		Cookie[] cookies = {new Cookie("anzahlAntworten", "3")};
		when(req.getCookies()).thenReturn(cookies);

		assertThat(getAnzahlAntworten(req)).isEqualTo(3);
	}

	@Test
	@DisplayName("Ein neues Cookie mit dem Namen anzahlAntworten kann erfolgreich mit dem Wert 4 erstellt werden")
	void test_7() {
		assertThat(createAnzahlAntwortenCookie(4).getValue()).isEqualTo("4");
	}

	@Test
	@DisplayName("Ein neues Cookie mit dem Namen frageTyp kann erfolgreich mit dem Wert NORMAL erstellt werden")
	void test_8() {
		assertThat(createFrageTypCookie(FrageTyp.NORMAL).getValue()).isEqualTo("NORMAL");
	}

	@Test
	@DisplayName("Falls kein Cookie mit dem Namen anzahlAntworten existiert, wird der Default-Wert 0 zurückgegeben")
	void test_9() {
		HttpServletRequest req = mock(HttpServletRequest.class);
		Cookie[] cookies = {new Cookie("iwasanders", "3")};
		when(req.getCookies()).thenReturn(cookies);

		assertThat(getAnzahlAntworten(req)).isEqualTo(0);
	}

	@Test
	@DisplayName("Aus einem SetFrageTyp-Request wird der Cookie mit dem Namen frageTyp erfolgreich upgedated")
	void test_10() {
		HttpServletResponse resp = mock(HttpServletResponse.class);
		SetFragetypRequest setFragetypRequest = new SetFragetypRequest("SINGLE_CHOICE", UUID.randomUUID().toString());
		doNothing().when(resp).addCookie(any(Cookie.class));

		updateFrageTypCookie(setFragetypRequest, resp);

		verify(resp).addCookie(new Cookie("frageTyp", setFragetypRequest.getFrageTyp()));
	}

	@Test
	@DisplayName("Aus einem Cookie mit dem Namen frageTyp kann der Wert geholt werden")
	void test_11() {
		HttpServletRequest req = mock(HttpServletRequest.class);
		Cookie[] cookies = {new Cookie("frageTyp", "SINGLE_CHOICE")};
		when(req.getCookies()).thenReturn(cookies);

		assertThat(getFrageTyp(req)).isEqualTo("SINGLE_CHOICE");
	}

	@Test
	@DisplayName("Falls kein Cookie mit dem Namen frageTyp existiert, wird der leere String als Default-Wert bekommen")
	void test_12() {
		HttpServletRequest req = mock(HttpServletRequest.class);
		Cookie[] cookies = {new Cookie("iwasanderes", "uoihWEG")};
		when(req.getCookies()).thenReturn(cookies);

		assertThat(getFrageTyp(req)).isEqualTo("");
	}
}
