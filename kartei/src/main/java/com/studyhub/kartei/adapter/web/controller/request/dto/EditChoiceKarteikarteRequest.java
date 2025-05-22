package com.studyhub.kartei.adapter.web.controller.request.dto;

import com.studyhub.kartei.domain.model.Antwort;

@Dto
public record EditChoiceKarteikarteRequest(
		String stapelId,
        String karteId,
        String frage,
		Antwort[] antworten,
        String notiz) {
}
