package com.studyhub.kartei.adapter.web.controller.request.dto;

@Dto
public record EditNormalKarteikarteRequest(
         String stapelId,
         String karteId,
         String frage,
         String antwort,
         String notiz) {
}
