package com.studyhub.track.domain.model.lernplan;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Tag {
	private DayOfWeek tag;
	private LocalTime beginn;
	private UUID sessionId;
}
