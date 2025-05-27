package com.studyhub.kartei.service.application;

import com.studyhub.kartei.adapter.web.controller.request.dto.Dto;

public record RemoveAntwortRequest(
		String stapelId,
		String karteId,
		Integer antwortIndex
) {
}
