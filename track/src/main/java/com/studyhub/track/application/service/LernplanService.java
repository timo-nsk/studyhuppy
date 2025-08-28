package com.studyhub.track.application.service;

import com.studyhub.track.adapter.web.controller.request.dto.LernplanResponse;
import com.studyhub.track.domain.model.lernplan.Lernplan;
import com.studyhub.track.domain.model.lernplan.TagDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LernplanService {

	private final LernplanRepository lernplanRepository;

	public LernplanService(LernplanRepository lernplanRepository) {
		this.lernplanRepository = lernplanRepository;
	}

	public boolean saveLernplan(Lernplan lernplan) {
		Lernplan saved = lernplanRepository.save(lernplan);
		return saved != null;
	}

	public LernplanResponse getActiveLernplanByUsername(String username) {
		Lernplan entityLerntag = lernplanRepository.findActiveByUsername(username);
		if (entityLerntag == null) {
			return null;
		}

		List<TagDto> tageList = entityLerntag.getTagesListe().stream()
				.map(e -> {
					String weekday = "";

					switch(e.getTag()) {
						case MONDAY -> weekday = "Montags";
						case TUESDAY -> weekday = "Dienstags";
						case WEDNESDAY -> weekday = "Mittwochs";
						case THURSDAY -> weekday = "Donnerstags";
						case FRIDAY -> weekday = "Freitags";
						case SATURDAY -> weekday = "Samstags";
						case SUNDAY -> weekday = "Sonntags";
					}

					return new TagDto(weekday, e.getBeginn().toString(), e.getSessionId().toString());

				}).toList();

		return new LernplanResponse(entityLerntag.getTitel(), tageList);
	}
}
