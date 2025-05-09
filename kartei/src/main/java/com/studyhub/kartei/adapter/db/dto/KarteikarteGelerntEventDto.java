package com.studyhub.kartei.adapter.db.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table("karteikarte_gelernt_event")
public record KarteikarteGelerntEventDto(@Id Integer id,
										 UUID stapelId,
                                         UUID karteikarteId,
                                         LocalDateTime gelerntAm,
                                         int secondsNeeded) {
}
