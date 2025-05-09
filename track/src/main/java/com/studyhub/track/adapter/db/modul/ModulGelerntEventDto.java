package com.studyhub.track.adapter.db.modul;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.UUID;

@Table("modul_gelernt_event")
public record ModulGelerntEventDto(
		@Id Integer id,
		UUID eventId,
        UUID modulId,
        String username,
        int secondsLearned,
        LocalDate dateGelernt) {
}
