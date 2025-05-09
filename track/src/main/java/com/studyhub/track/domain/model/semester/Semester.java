package com.studyhub.track.domain.model.semester;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class Semester {
	Integer modul;
	SemesterTyp semesterTyp;
	LocalDate vorlesungBeginn;
	LocalDate vorlesungEnde;
	LocalDate semesterBeginn;
	LocalDate semesterEnde;
}
