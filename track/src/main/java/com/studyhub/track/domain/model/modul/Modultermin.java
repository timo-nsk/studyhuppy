package com.studyhub.track.domain.model.modul;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Modultermin {
	private String terminName;
	private LocalDateTime startDate;
	private LocalDateTime endeDate;
	private String notiz;
	private Terminart terminArt;
	private Terminfrequenz terminfrequenz;
}
