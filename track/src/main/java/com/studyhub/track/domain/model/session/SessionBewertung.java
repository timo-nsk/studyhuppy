package com.studyhub.track.domain.model.session;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SessionBewertung {
	Double konzentrationBewertung;
	Double produktivitaetBewertung;
	Double schwierigkeitBewertung;
}
