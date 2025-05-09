package com.studyhub.kartei.adapter.web;

import com.studyhub.kartei.adapter.web.controller.SetFragetypRequest;
import com.studyhub.kartei.domain.model.FrageTyp;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * This class provides methods for the Cookie-Management in the process of creating a new Karteikarte.
 */
public class CookieHandler {

	private final static String COOKIE_NAME_FRAGETYP = "frageTyp";
	private final static String COOKIE_NAME_ANZAHLANTWORTEN = "anzahlAntworten";

	public static Cookie createFrageTypCookie(FrageTyp frageTyp) {
		Cookie c = new Cookie(COOKIE_NAME_FRAGETYP, frageTyp.toString());
		c.setPath("/");
		return c;
	}

	public static boolean frageTypCookieExists(HttpServletRequest req) {
		for (Cookie cookie: req.getCookies()) if(COOKIE_NAME_FRAGETYP.equals(cookie.getName())) return true;

		return false;
	}

	public static String getFrageTyp(HttpServletRequest req) {
		for (Cookie cookie: req.getCookies()) if(COOKIE_NAME_FRAGETYP.equals(cookie.getName())) return cookie.getValue();
		return "";
	}

	public static void updateFrageTypCookie(SetFragetypRequest request, HttpServletResponse resp) {
		resp.addCookie(new Cookie(COOKIE_NAME_FRAGETYP, request.getFrageTyp()));
	}

	public static Cookie createAnzahlAntwortenCookie(Integer anzahlAntworten) {
		Cookie c = new Cookie(COOKIE_NAME_ANZAHLANTWORTEN, String.valueOf(anzahlAntworten));
		c.setPath("/");
		return c;
	}

	public static Integer getAnzahlAntworten(HttpServletRequest req) {
		for (Cookie cookie: req.getCookies()) {
			if(COOKIE_NAME_ANZAHLANTWORTEN.equals(cookie.getName())) {
				return Integer.parseInt(cookie.getValue());
			}
		}
		return 0;
	}

	public static boolean anzahlAntwortenCookieExists(HttpServletRequest req) {
		for (Cookie cookie: req.getCookies()) {
			if(COOKIE_NAME_ANZAHLANTWORTEN.equals(cookie.getName())) {
				return true;

			}
		}
		return false;
	}
}
