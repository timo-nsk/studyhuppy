package com.studyhub.track.adapter.web.controller.request.dto;

import com.studyhub.track.domain.model.lernplan.Lernplan;
import com.studyhub.track.domain.model.lernplan.Tag;
import com.studyhub.track.domain.model.lernplan.TagDto;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public record LernplanRequest(
		String lernplanTitel,
		List<TagDto> tage
) {
	public Lernplan toEntity(String username) {
		List<Tag> tagesListe = tage.stream()
				.filter(e -> !e.session().equals("none"))
				.map(dtoObj -> {

					DayOfWeek tag;

					switch(dtoObj.weekday()) {
						case "Montags" -> tag = DayOfWeek.MONDAY;
						case "Dienstags" -> tag = DayOfWeek.TUESDAY;
						case "Mittwochs" -> tag = DayOfWeek.WEDNESDAY;
						case "Donnerstags" -> tag = DayOfWeek.THURSDAY;
						case "Freitags" -> tag = DayOfWeek.FRIDAY;
						case "Samstags" -> tag = DayOfWeek.SATURDAY;
						case "Sonntags" -> tag = DayOfWeek.SUNDAY;
						default -> tag = null;
					}

					LocalTime beginn = LocalTime.parse(dtoObj.beginn());


					return new Tag(tag, beginn, UUID.fromString(dtoObj.session()));
				})
				.toList();

		return Lernplan.builder()
				.fachId(UUID.randomUUID())
				.username(username)
				.titel(lernplanTitel)
				.tagesListe(tagesListe)
				.isActive(false)
				.build();
	}
}
