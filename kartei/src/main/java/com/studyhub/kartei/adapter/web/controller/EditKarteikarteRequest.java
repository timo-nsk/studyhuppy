package com.studyhub.kartei.adapter.web.controller;

import com.studyhub.kartei.domain.model.FrageTyp;

public record EditKarteikarteRequest(
         String stapelId,
         String karteId,
         String frage,
         String antwort,
         String notiz) {
}
