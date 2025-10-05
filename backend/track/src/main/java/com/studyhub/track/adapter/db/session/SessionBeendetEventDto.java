package com.studyhub.track.adapter.db.session;

import com.studyhub.track.domain.model.session.SessionBewertung;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table("session_beendet_event")
public record SessionBeendetEventDto(
		@Id Long id,
		UUID eventId,
		String username,
		LocalDateTime beendetDatum,
		SessionBewertung bewertung,
		Boolean abgebrochen
) {
}
