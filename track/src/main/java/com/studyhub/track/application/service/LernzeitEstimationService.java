package com.studyhub.track.application.service;

import com.studyhub.track.domain.model.modul.Modul;
import com.studyhub.track.domain.model.semester.SemesterPhase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

//TODO: implement this functionality
@Service
public class LernzeitEstimationService {
	private final Logger logger = LoggerFactory.getLogger(LernzeitEstimationService.class);

	public int getAverageLernzeitPerLerntagForModul(Modul modul, SemesterPhase phase) {
		int remainingSelbststudiumZeit = modul.getRemainingSelbststudiumZeit();
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
			default: return 0;
		}

		int weeksBetween = (int) ChronoUnit.WEEKS.between(from, to);
		int gesamtLerntage = countTage * weeksBetween;
		int res = remainingSelbststudiumZeit / gesamtLerntage;

		logger.info("Calculated average lernzeit per lerntag for modul '%s' in phase '%s': %d minutes".formatted(modul.getName(), phase, res));
		return res;
	}
}
