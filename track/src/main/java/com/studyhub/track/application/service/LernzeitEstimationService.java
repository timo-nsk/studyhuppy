package com.studyhub.track.application.service;

import com.studyhub.track.domain.model.modul.Modul;
import com.studyhub.track.domain.model.semester.SemesterPhase;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class LernzeitEstimationService {

	public int getAverageLernzeitPerLerntagForModul(Modul modul, SemesterPhase phase) {
		int remainingSelbststudiumZeit = modul.getRemainingSelbststudiumZeit();
		System.out.println(remainingSelbststudiumZeit);
		boolean[] lerntage = modul.getLerntage().getAllLerntage();
		int countTage = 0;
		for(boolean tag : lerntage) if(tag) countTage++;
		LocalDate from = null;
		LocalDate to = null;

		switch (phase) {
			case VORLESUNG: {
				if(!modul.vorlesungDatumangabenEingetragen()) return 0;
				from = modul.getSemester().getVorlesungBeginn();
				to = modul.getSemester().getVorlesungEnde();
				break;
			}
			case KLAUSUR: {
				if(!modul.klausurDatumEingetragen()) return 0;
				from = modul.getSemester().getVorlesungEnde();
				to = modul.getKlausurDate().toLocalDate();
				break;
			}
			default: return 0;
		}

		int weeksBetween = (int) ChronoUnit.WEEKS.between(from, to);
		int gesamtLerntage = countTage * weeksBetween;

		return remainingSelbststudiumZeit / gesamtLerntage;
	}
}
