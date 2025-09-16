package com.studyhub.track.domain.model.session;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class SessionBeendetEvent {
	UUID eventId;
	String username;
	LocalDateTime beendetDatum;
	SessionBewertung bewertung;
	Boolean abgebrochen;
}
