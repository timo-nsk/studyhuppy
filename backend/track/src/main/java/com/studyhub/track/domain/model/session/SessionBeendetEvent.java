package com.studyhub.track.domain.model.session;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * This event contains all the information when the user finished a learning session.
 *
 * <ul>
 *   <li>{@code eventId} – Unique ID of the event</li>
 *   <li>{@code sessionId} – ID of the session</li>
 *   <li>{@code username} – Username of the user</li>
 *   <li>{@code beendetDatum} – Timestamp when the session was finished</li>
 *   <li>{@code bewertung} – Subjective rating of the user for his performance of the learning session.</li>
 *   <li>{@code abgebrochen} – {@code true}, if the session was aborted by the user</li>
 * </ul>
 */
@Data
@AllArgsConstructor
public class SessionBeendetEvent {
	UUID eventId;
	UUID sessionId;
	String username;
	LocalDateTime beendetDatum;
	SessionBewertung bewertung;
	Boolean abgebrochen;
}
