package com.studyhub.kartei.service.application;

public record EditNormalKarteikarteRequest(
         String stapelId,
         String karteId,
         String frage,
         String antwort,
         String notiz) {
}
