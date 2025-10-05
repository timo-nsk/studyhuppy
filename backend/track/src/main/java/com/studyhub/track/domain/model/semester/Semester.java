package com.studyhub.track.domain.model.semester;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Semester {
	Integer modul;
	Integer fachSemester;
	SemesterTyp semesterTyp;
	LocalDate vorlesungBeginn;
	LocalDate vorlesungEnde;
	LocalDate semesterBeginn;
	LocalDate semesterEnde;
}
