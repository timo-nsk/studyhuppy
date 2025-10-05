package com.studyhub.kartei.service.application;

import com.studyhub.kartei.domain.model.Antwort;

public record EditChoiceKarteikarteRequest(
		String stapelId,
        String karteId,
        String frage,
		Antwort[] antworten,
        String notiz) {
}
