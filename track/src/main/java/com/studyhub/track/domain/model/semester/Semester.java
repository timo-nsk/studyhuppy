package com.studyhub.track.domain.model.semester;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Semester {
	Integer modul;
	SemesterTyp semesterTyp;
	LocalDate vorlesungBeginn;
	LocalDate vorlesungEnde;
	LocalDate semesterBeginn;
	LocalDate semesterEnde;
}
