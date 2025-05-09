package com.studyhub.track.adapter.web;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record KlausurDateRequest(String fachId,
                                 @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
								 // TODO: Validieren(?)
                                 String time) {
}
