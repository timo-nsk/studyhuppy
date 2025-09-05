package com.studyhub.track.application.service;

import com.studyhub.track.application.JWTService;
import com.studyhub.track.domain.model.modul.Modul;
import com.studyhub.track.domain.model.modul.ModulGelerntEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;

@Service
public class ModulEventService {
	private final ModulGelerntEventRepository modulGelerntEvent;
	private final ModulRepository modulRepository;
	private final JWTService jwtService;
	private final DateProvider dateProvider;
	private final Logger logger = LoggerFactory.getLogger(ModulEventService.class);

	public ModulEventService(ModulGelerntEventRepository modulGelerntEvent, ModulRepository modulRepository, JWTService jwtService, DateProvider dateProvider) {
		this.modulGelerntEvent = modulGelerntEvent;
		this.modulRepository = modulRepository;
		this.jwtService = jwtService;
		this.dateProvider = dateProvider;
	}

	public void saveEvent(int secondsLearned, String modulId, String username) {
		if (secondsLearned < 1) return;
		ModulGelerntEvent event = ModulGelerntEvent.initEvent(UUID.fromString(modulId), secondsLearned, username);
		modulGelerntEvent.save(event);
		logger.info("Saved ModulGelerntEvent: {}", event);
	}

	public Map<LocalDate, List<ModulStat>> getStatisticsForRecentDays(int days, String username) {
		List<Modul> modules = modulRepository.findByUsername(username);
		Map<LocalDate, List<ModulStat>> dataMap = new TreeMap<>();

		for (int i = 0; i < days; i++) {
			LocalDate date = dateProvider.getTodayDate().minusDays(i);
			List<ModulStat> statistics = new LinkedList<>();
			for (Modul modul : modules) {
				int seconds = modulGelerntEvent.getSumSecondsLearned(date, username, modul.getFachId());
				if(seconds > 0) {
					ModulStat stat =  new ModulStat(modul.getName(), String.valueOf(seconds));
					statistics.add(stat);
				}
			}

			if (statistics.isEmpty()) continue;

			dataMap.put(date, statistics);
		}

		logger.info("Generated statistics for the last {} days for user '{}': {}", days, username, dataMap);
		return dataMap;
	}

	public Integer computeAverageStudyTimePerDay(String username) {
		List<ModulGelerntEvent> allEvents = modulGelerntEvent.getAllByUsername(username);
		int N = (int) allEvents.stream().map(ModulGelerntEvent::dateGelernt).distinct().count();
		int sum = allEvents.stream().mapToInt(ModulGelerntEvent::secondsLearned).sum();

		if(N == 0) return 0;

		logger.info("Computed average study time per day for user '{}': {} seconds over {} days", username, sum, N);
		return (int) Math.round((double) sum / N);
	}

	public void deleteAllModulEvents(UUID modulId) {
		modulGelerntEvent.deleteAllByModulId(modulId);
	}
}
