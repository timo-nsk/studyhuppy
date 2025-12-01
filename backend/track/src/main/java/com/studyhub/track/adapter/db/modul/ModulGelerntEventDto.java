package com.studyhub.track.adapter.db.modul;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.UUID;

@Table("MODUL_GELERNT_EVENT")
public record ModulGelerntEventDto(
		@Id Integer id,
		UUID eventId,
        UUID modulId,
        String username,
        int secondsLearned,
        LocalDate dateGelernt) {
}
