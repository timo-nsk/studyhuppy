package com.studyhub.track.application.service;

import com.studyhub.jwt.JWTService;
import com.studyhub.track.adapter.web.ModulUpdateRequest;
import com.studyhub.track.domain.model.modul.Modul;
import com.studyhub.track.domain.model.modul.ModulGelerntEvent;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;

@Service
public class ModulEventService {
	private final ModulGelerntEventRepository modulGelerntEvent;
	private final ModulRepository modulRepository;
	private final JWTService jwtService;
	private final DateProvider dateProvider;

	public ModulEventService(ModulGelerntEventRepository modulGelerntEvent, ModulRepository modulRepository, JWTService jwtService, DateProvider dateProvider) {
		this.modulGelerntEvent = modulGelerntEvent;
		this.modulRepository = modulRepository;
		this.jwtService = jwtService;
		this.dateProvider = dateProvider;
	}

	public void saveEvent(ModulUpdateRequest request, String token) {
		if (request.secondsLearnedThisSession() < 1) return;
		ModulGelerntEvent event = ModulGelerntEvent.initEvent(UUID.fromString(request.fachId()), request.secondsLearnedThisSession(), jwtService.extractUsername(token));
		modulGelerntEvent.save(event);
	}

	public Map<LocalDate, List<ModulStat>> getStatisticsForRecentDays(int days, String token) {
		String username = jwtService.extractUsername(token);
		List<Modul> modules = modulRepository.findByUsername(username);
		Map<LocalDate, List<ModulStat>> dataMap = new HashMap<>();

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
		return dataMap;
	}
}
