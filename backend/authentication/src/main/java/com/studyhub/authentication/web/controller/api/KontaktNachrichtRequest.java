package com.studyhub.authentication.web.controller.api;

import com.studyhub.authentication.model.kontakt.BearbeitungStatus;
import com.studyhub.authentication.model.kontakt.Betreff;
import com.studyhub.authentication.model.kontakt.KontaktNachricht;

import java.util.UUID;

public record KontaktNachrichtRequest(Betreff betreff, String nachricht) {

    public KontaktNachricht toKontaktNachricht() {
        return new KontaktNachricht(null, UUID.randomUUID(), betreff, nachricht, BearbeitungStatus.OPEN);
    }
}
