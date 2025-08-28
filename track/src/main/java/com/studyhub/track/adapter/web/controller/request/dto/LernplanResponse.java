package com.studyhub.track.adapter.web.controller.request.dto;

import com.studyhub.track.domain.model.lernplan.TagDto;

import java.util.List;

public record LernplanResponse(
		String titel,
		List<TagDto> tagesListe
) {
}
