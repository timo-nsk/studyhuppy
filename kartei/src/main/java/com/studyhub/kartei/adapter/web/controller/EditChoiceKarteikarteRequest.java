package com.studyhub.kartei.adapter.web.controller;

import com.studyhub.kartei.domain.model.Antwort;

public record EditChoiceKarteikarteRequest(
		String stapelId,
        String karteId,
        String frage,
		Antwort[] antworten,
        String notiz) {
}
