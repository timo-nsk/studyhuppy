package com.studyhub.track.application.service.dto;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public record LernplanWochenuebersicht(
		String lernplanTitel,
		List<LernplanTagesuebersicht> sessionList
) {

	public boolean isTodayPlanned() {
		DayOfWeek currentDayOfWeek = LocalDate.now().getDayOfWeek();

		for(LernplanTagesuebersicht session : sessionList) {
			DayOfWeek plannedDayOfWeek = mapToEnum(session.weekday());
			if (currentDayOfWeek == plannedDayOfWeek && !session.blocks().isEmpty()) {
				return true;
			}
		}
		return false;
	}

	private DayOfWeek mapToEnum(String weekday) {
		return switch (weekday) {
			case "Montags" -> DayOfWeek.MONDAY;
			case "Dienstags" -> DayOfWeek.TUESDAY;
			case "Mittwochs" -> DayOfWeek.WEDNESDAY;
			case "Donnerstags" -> DayOfWeek.THURSDAY;
			case "Freitags" -> DayOfWeek.FRIDAY;
			case "Samstags" -> DayOfWeek.SATURDAY;
			default -> DayOfWeek.SUNDAY;
		};
	}
}