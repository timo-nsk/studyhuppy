package com.studyhub.kartei.util;

import org.jsoup.Jsoup;

public class JsoupUtil {

	public static String getTextFromElementById(String html, String id) {
		return Jsoup.parse(html).getElementById(id).text();
	}
}
