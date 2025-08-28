package com.studyhub.track.adapter.web.controller.request.dto;

import com.studyhub.track.domain.model.lernplan.TagDto;

import java.util.List;

public record LernplanRequest(
		String lernplanTitel,
		List<TagDto> tage
) {
}
