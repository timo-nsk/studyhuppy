package com.studyhub.track.adapter.web.controller.request.dto;

import com.studyhub.track.domain.model.session.Session;

import java.util.List;

public record LernplanResponse(
		String lernplanTitel,
		List<LernplanSessionInfoDto> sessionList
) {
}
