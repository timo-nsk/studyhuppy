package com.studyhub.mail.domain.model;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.UUID;

public record MailGesendetEvent(
		@Id Integer id,
		UUID userId,
		LocalDateTime gesendetAm,
		MailTyp typ,
		boolean erfolgreichVersendet
		) {

	public static MailGesendetEvent createEventSuccess(MailTyp typ, boolean erfolgreichVersendet) {
		// user id soll später auch korrekt gefetched werden
		return new MailGesendetEvent(null, UUID.randomUUID(), LocalDateTime.now(), typ, erfolgreichVersendet);
	}

}
