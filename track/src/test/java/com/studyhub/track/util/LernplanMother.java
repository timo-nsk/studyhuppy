package com.studyhub.track.util;

import com.studyhub.track.domain.model.lernplan.Lernplan;
import com.studyhub.track.domain.model.lernplan.Tag;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LernplanMother {

	public static Lernplan initFullLernplan() {

		List<Tag> tage = new ArrayList<>();

		for(int i = 1; i <= 7; i++) {
			Tag t = new Tag(DayOfWeek.of(i), LocalTime.of(10, i, 0), UUID.randomUUID());
			tage.add(t);
		}

		return Lernplan.builder()
				.fachId(UUID.randomUUID())
				.username("testuser")
				.titel("Test Lernplan")
				.tagesListe(tage)
				.isActive(true)
				.build();
	}
}
