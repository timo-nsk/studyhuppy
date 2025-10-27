package com.studyhub.track.util;

import com.studyhub.track.application.service.dto.LernplanTagesuebersicht;
import com.studyhub.track.application.service.dto.LernplanWochenuebersicht;
import com.studyhub.track.domain.model.lernplan.Lernplan;
import com.studyhub.track.domain.model.lernplan.Tag;
import com.studyhub.track.domain.model.session.Session;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.studyhub.track.util.SessionMother.createSessionWithNRandomBlocks;
import static com.studyhub.track.util.SessionMother.createSessionWithTwoBlocks;

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

	public static Lernplan initFullLernplan(UUID specificSessionId) {

		List<Tag> tage = new ArrayList<>();

		for(int i = 1; i <= 7; i++) {
			Tag t = new Tag(DayOfWeek.of(i), LocalTime.of(10, i, 0), specificSessionId);
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

	public static Lernplan initFullLernplanWithRandomSessions(UUID andSpecificSessionId) {

		List<Tag> tage = new ArrayList<>();

		for(int i = 1; i <= 7; i++) {
			if(i == 2) {
				Tag t = new Tag(DayOfWeek.of(i), LocalTime.of(10, i, 0), andSpecificSessionId);
				tage.add(t);
			} else {
				Tag t = new Tag(DayOfWeek.of(i), LocalTime.of(10, i, 0), UUID.randomUUID());
				tage.add(t);
			}
		}

		return Lernplan.builder()
				.fachId(UUID.randomUUID())
				.username("testuser")
				.titel("Test Lernplan")
				.tagesListe(tage)
				.isActive(true)
				.build();
	}

	public static LernplanWochenuebersicht initFullLernplanWochenuebersicht(UUID specificSessionId) {
		List<LernplanTagesuebersicht> tagesUebersichtList = new ArrayList<>();

		Session s = createSessionWithTwoBlocks(specificSessionId);
		LernplanTagesuebersicht montag = new LernplanTagesuebersicht("Montags", "10:01", specificSessionId.toString(), s.getBlocks());
		LernplanTagesuebersicht dienstag = new LernplanTagesuebersicht("Dienstags", "10:02", specificSessionId.toString(), s.getBlocks());
		LernplanTagesuebersicht mittwoch = new LernplanTagesuebersicht("Mittwochs", "10:03", specificSessionId.toString(), s.getBlocks());
		LernplanTagesuebersicht donnerstag = new LernplanTagesuebersicht("Donnerstags", "10:04", specificSessionId.toString(), s.getBlocks());
		LernplanTagesuebersicht freitag = new LernplanTagesuebersicht("Freitags", "10:05", specificSessionId.toString(), s.getBlocks());
		LernplanTagesuebersicht samstag = new LernplanTagesuebersicht("Samstags", "10:06", specificSessionId.toString(), s.getBlocks());
		LernplanTagesuebersicht sonntag = new LernplanTagesuebersicht("Sonntags", "10:07", specificSessionId.toString(), s.getBlocks());
		tagesUebersichtList.add(montag);
		tagesUebersichtList.add(dienstag);
		tagesUebersichtList.add(mittwoch);
		tagesUebersichtList.add(donnerstag);
		tagesUebersichtList.add(freitag);
		tagesUebersichtList.add(samstag);
		tagesUebersichtList.add(sonntag);

		return new LernplanWochenuebersicht("Test Lernplan", tagesUebersichtList);
	}
}
