package com.studyhub.kartei.adapter.web.controller.request.dto;

import com.studyhub.kartei.domain.model.Antwort;
import com.studyhub.kartei.domain.model.FrageTyp;
import com.studyhub.kartei.domain.model.Karteikarte;
import java.util.Arrays;

public record NewChoiceKarteikarteRequest(
        String stapelId,
        FrageTyp frageTyp,
        String frage,
        Antwort[] antworten,
        String notiz
) {
    public Karteikarte buildChoiceKarte() {
        Karteikarte k = Karteikarte.initNewKarteikarte(frage, null, notiz, frageTyp);
        k.setAntworten(Arrays.stream(antworten).toList());
        return k;
    }
}
