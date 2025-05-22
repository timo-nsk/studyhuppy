package com.studyhub.kartei.adapter.web.controller;

public record EditNormalKarteikarteRequest(
         String stapelId,
         String karteId,
         String frage,
         String antwort,
         String notiz) {
}
