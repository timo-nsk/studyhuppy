package com.studyhub.track.adapter.web;

import com.studyhub.track.domain.model.modul.Kreditpunkte;
import com.studyhub.track.domain.model.modul.Modul;
import com.studyhub.track.domain.model.semester.Semester;
import com.studyhub.track.domain.model.semester.SemesterTyp;
import java.time.LocalDate;
import java.util.UUID;

public record ModulForm(
		String name,
		Integer creditPoints,
		Integer kontaktzeitStunden,
		Integer selbststudiumStunden,
		LocalDate klausurDatum,
		String time

		/** Für Stapel anlegen
		 Boolean stapelCheckbox,
		 String stapelName,
		 String beschreibung,
		 String lernstufen,

		 Für Semester
		 Boolean semesterCheckbox,
		 LocalDate semesterBeginn,
		 LocalDate semesterEnde,
		 LocalDate vlBeginn,
		 LocalDate vlEnde)
		 **/
)
{
	public Modul newModulFromFormData(ModulForm modulForm, String username, int semester) {
		Kreditpunkte kreditpunkte = new Kreditpunkte(modulForm.creditPoints(), modulForm.kontaktzeitStunden(), modulForm.selbststudiumStunden());
		//TODO: Semester objekt erstellen
		return new Modul(UUID.randomUUID(), name, 0, kreditpunkte, username, true, semester, new Semester(), null);
	}

	public SemesterTyp decideSemesterTyp(LocalDate semesterBeginn) {
		if(semesterBeginn.isAfter(LocalDate.of(LocalDate.now().getYear(), 2, 1)) && semesterBeginn.isBefore(LocalDate.of(LocalDate.now().getYear(), 8, 1))) {
			return SemesterTyp.SOMMERSEMESTER;
		} else if(semesterBeginn.isAfter(LocalDate.of(LocalDate.now().getYear(), 8, 1)) && semesterBeginn.isBefore(LocalDate.of(LocalDate.now().getYear()+1, 2, 1))) {
			return SemesterTyp.WINTERSEMESTER;
		}
		return null;
	}
}