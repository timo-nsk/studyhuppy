package com.studyhub.track.application.service.dto;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public record LernplanResponse(
		String lernplanTitel,
		List<LernplanSessionInfoDto> sessionList
) {

	public boolean isTodayPlanned() {
		boolean todayPlanned = false;
		DayOfWeek currentDayOfWeek = LocalDate.now().getDayOfWeek();

		for(LernplanSessionInfoDto session : sessionList) {
			DayOfWeek plannedDayOfWeek = mapToEnum(session.weekday());
			if (currentDayOfWeek == plannedDayOfWeek && !session.blocks().isEmpty()) {
				todayPlanned = true;
				break;
			}
		}
		return todayPlanned;
	}

	private DayOfWeek mapToEnum(String weekday) {
		if(weekday.equals("Montags")) {
			return DayOfWeek.MONDAY;
		} else if(weekday.equals("Dienstags")) {
			return DayOfWeek.TUESDAY;
		} else if(weekday.equals("Mittwochs")) {
			return DayOfWeek.WEDNESDAY;
		} else if(weekday.equals("Donnerstags")) {
			return DayOfWeek.THURSDAY;
		} else if(weekday.equals("Freitags")) {
			return DayOfWeek.FRIDAY;
		} else if(weekday.equals("Samstags")) {
			return DayOfWeek.SATURDAY;
		} else  {
			return DayOfWeek.SUNDAY;
		}
	}


}
