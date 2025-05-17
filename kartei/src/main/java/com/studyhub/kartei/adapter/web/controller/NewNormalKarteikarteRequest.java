package com.studyhub.kartei.adapter.web.controller;

public record NewNormalKarteikarteRequest(
        String stapelId,
        String frage,
        String antwort,
        String notiz
) {
}
