package com.studyhub.track.adapter.web;

import com.studyhub.jwt.JWTService;
import com.studyhub.track.adapter.web.validation.ValidKlausurDatum;
import com.studyhub.track.application.service.TimeConverter;
import com.studyhub.track.domain.model.modul.Kreditpunkte;
import com.studyhub.track.domain.model.modul.Lerntage;
import com.studyhub.track.domain.model.modul.Modul;
import com.studyhub.track.domain.model.semester.Semester;
import com.studyhub.track.domain.model.semester.SemesterPhase;
import com.studyhub.track.domain.model.semester.SemesterTyp;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@ValidKlausurDatum
public record ModulForm(
		@NotEmpty(message = "Geben Sie einen Modulnamen ein")
		String name,

		@Min(value = 1, message = "Mindestens 1 Credit-Punkte muss angegeben werden")
		@Max(value = 30, message = "Maximal 30 Credit-Punkte")
		@Positive(message = "Credit-Punkte müssen positiv sein von 1 bis 30")
		Integer creditPoints,
		Integer kontaktzeitStunden,
		Integer selbststudiumStunden,
		LocalDate klausurDatum,
		String time,

		//Für Lerntage
		Boolean lerntageCheckbox,
		Boolean mondays,
		Boolean tuesdays,
		Boolean wednesdays,
		Boolean thursdays,
		Boolean fridays,
		Boolean saturdays,
		Boolean sundays,

		//Für Stapel anlegen
		Boolean stapelCheckbox,
		String stapelName,
		String beschreibung,
		String lernstufen,

		//Für Semester
		Boolean semesterCheckbox,
		LocalDate semesterBeginn,
		LocalDate semesterEnde,
		LocalDate vlBeginn,
		LocalDate vlEnde)
{
	public Modul newModulFromFormData(ModulForm modulForm, int semesterstufe, String token) {
		JWTService jwtService = new JWTService();
		//TODO: Semester objekt erstellen
		Lerntage lerntage = new Lerntage(mondays, tuesdays, wednesdays, thursdays, fridays, saturdays, sundays, SemesterPhase.VORLESUNG);


		LocalDateTime actualKlausurDatum = null;

		if (klausurDatum != null) {
			actualKlausurDatum = LocalDateTime.of(modulForm.klausurDatum(), TimeConverter.getLocalTimeFromString(time));
		}



		Kreditpunkte kreditpunkte = new Kreditpunkte(modulForm.creditPoints(), modulForm.kontaktzeitStunden(), modulForm.selbststudiumStunden());


		Semester semester = null;

		if(semesterCheckbox) {
			SemesterTyp semesterPhase = decideSemesterTyp(semesterBeginn);
			semester = new Semester(null, semesterPhase, vlBeginn, vlEnde, semesterBeginn, semesterEnde);
		}

		return new Modul(UUID.randomUUID(), modulForm.name(), 0, kreditpunkte, jwtService.extractUsername(token),true, semesterstufe, semester, actualKlausurDatum, lerntage);
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
