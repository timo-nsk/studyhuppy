package com.studyhub.track.domain.model.session;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SessionBewertung {
	Integer konzentrationBewertung;
	Integer produktivitaetBewertung;
	Integer schwierigkeitBewertung;
}
