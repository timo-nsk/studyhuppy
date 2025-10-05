package com.studyhub.kartei.util;

import com.studyhub.kartei.domain.model.Antwort;
import com.studyhub.kartei.domain.model.FrageTyp;
import com.studyhub.kartei.domain.model.Karteikarte;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class KarteikarteMother {
	public static Karteikarte newKarteikarte(String frage, String antwort) {
		return new Karteikarte(
				UUID.randomUUID(),
				frage,
				antwort,
				new ArrayList<>(),
				LocalDateTime.now(),
				LocalDateTime.now(),
				LocalDateTime.now(),
				"Notiz",
				0,
				FrageTyp.NORMAL,
				10,
				"10m,10h,10d");
	}

	public static Karteikarte newKarteikarteWithFälligAm(LocalDateTime fälligAm) {
		return new Karteikarte(
				UUID.randomUUID(),
				"frage",
				"antwort",
				new ArrayList<>(),
				LocalDateTime.now(),
				LocalDateTime.now(),
				fälligAm,
				"Notiz",
				1,
				FrageTyp.NORMAL,
				10,
				"10m,10h,10d");
	}

	public static Karteikarte newKarteikarteWithLevel(int level) {
		return new Karteikarte(
				UUID.randomUUID(),
				"frage",
				"antwort",
				new ArrayList<>(),
				LocalDateTime.now(),
				LocalDateTime.now(),
				LocalDateTime.now(),
				"Notiz",
				level,
				FrageTyp.NORMAL,
				10,
				"10m,10h,10d");
	}

	public static Karteikarte newKarteikarteWithLevelAndLernstufen(int level, String lernstufen) {
		return new Karteikarte(
				UUID.randomUUID(),
				"frage",
				"antwort",
				new ArrayList<>(),
				LocalDateTime.now(),
				LocalDateTime.now(),
				LocalDateTime.now(),
				"Notiz",
				level,
				FrageTyp.NORMAL,
				10,
				lernstufen);
	}

	public static Karteikarte newKarteikarteWithLernstufen(String lernstufen) {
		return new Karteikarte(
				UUID.randomUUID(),
				"frage",
				"antwort",
				new ArrayList<>(),
				LocalDateTime.now(),
				LocalDateTime.now(),
				LocalDateTime.now(),
				"Notiz",
				0,
				FrageTyp.NORMAL,
				10,
				lernstufen);
	}

	public static Karteikarte newKarteWithIdAndFälligAm(UUID karteId, LocalDateTime fällig) {
		return new Karteikarte(
				karteId,
				"frage",
				"antwort",
				new ArrayList<>(),
				LocalDateTime.now(),
				LocalDateTime.now(),
				fällig,
				"Notiz",
				0,
				FrageTyp.NORMAL,
				10,
				"2m,2h,2d");
	}

	public static Karteikarte newKarteWithAntworten(UUID karteId, int antworten) {
		Karteikarte k = newKarteikarte("frage", "antwort");

		for (int i = 0; i < antworten; i++) {
			Antwort a = new Antwort("aw" + i, true);
			k.getAntworten().add(a);
		}

		return k;
	}
}
