package com.studyhub.track.application.service;

import com.studyhub.track.domain.model.modul.Modul;
import com.studyhub.track.domain.model.semester.SemesterPhase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LernzeitEstimationService {
	private final Logger logger = LoggerFactory.getLogger(LernzeitEstimationService.class);

	public int getAverageLernzeitPerLerntagForModul(Modul modul, SemesterPhase phase) {
		// TODO: rework
		return -1;
	}
}
