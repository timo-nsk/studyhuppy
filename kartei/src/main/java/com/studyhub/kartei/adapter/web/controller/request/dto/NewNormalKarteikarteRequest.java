package com.studyhub.kartei.adapter.web.controller.request.dto;

import com.studyhub.kartei.domain.model.FrageTyp;
import com.studyhub.kartei.domain.model.Karteikarte;

public record NewNormalKarteikarteRequest(
        String stapelId,
        FrageTyp frageTyp,
        String frage,
        String antwort,
        String notiz
) {
    public Karteikarte buildNormaleKarte() {
        return Karteikarte.initNewKarteikarte(frage, antwort, notiz, FrageTyp.NORMAL);
    }
}
