package com.studyhub.track.domain.model.session;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Contains information about the users subjective rating of their learning sessions.
 *
 * <ul>
 *     <li>{@code konzentrationBewertung} - Rating of the users ability to hold concentration during the session</li>
 *     <li>{@code produktivitaetBewertung}  - Rating of the users feeling of productivity during the session</li>
 *     <li>{@code schwierigkeitBewertung}  - Rating of how difficult the topics of the session were</li>
 * </ul>
 */
@Data
@AllArgsConstructor
public class SessionBewertung {
	Integer konzentrationBewertung;
	Integer produktivitaetBewertung;
	Integer schwierigkeitBewertung;
}
